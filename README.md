OkHttpMockTest
==============

Test the OkHttpClient using MockWebServerRule.

```java
@RunWith(RobolectricTestRunner.class)
public class ItemObservableTest {

  @Module(injects = ItemObservableTest.class,
  includes = AppModule.class,
  overrides = true)
  class TestModule {

  }

  @Rule
  public MockWebServerRule server = new MockWebServerRule();

  @Inject
  ItemObservable itemObservable;

  @Before
  public void setUp() throws Exception {
    ObjectGraph graph = ObjectGraph.create(
    new TestModule());
    graph.inject(this);
  }

  @Test
  public void testFromId() throws Exception {
    File file = new File("src/androidTest/assets/item.json");
    String json = FileUtils.readFileToString(file);

    MockResponse response = new MockResponse()
    .setResponseCode(200)
    .setBody(json);
    server.enqueue(response);

    itemObservable = spy(itemObservable);
    when(itemObservable.buildPath(anyInt())).thenReturn(server.getUrl("/").toString());

    Item item = itemObservable.fromId(1).toBlocking().single();
    assertThat(item, notNullValue());
    assertThat(item.getId(), is(10));
    assertThat(item.getName(), is("tomato"));
    assertThat(item.getDescription(), is("It is super sweet tomato!"));
    assertThat(item.getPrice(), is(98));
  }
}
```

Take a look at the code for more information :)
