import com.example.{CacheHelper, Command}
import org.mockito.Matchers._
import org.mockito.Mockito._
import org.scalatest.{Matchers, _}
import org.scalatest.mock.MockitoSugar

class ExampleSpec extends FlatSpec with Matchers with BeforeAndAfter with MockitoSugar {

  "it" should "call commands execute" in {
    val cacheHelper: CacheHelper = new CacheHelper
    val commandMock: Command = mock[Command]
    val spyCacheHelper = spy(cacheHelper)

    when(spyCacheHelper.isCircuitBreakerEnabled).thenReturn(true)
    //    when(spyCacheHelper.createCommand("key", any[Function0[Long]])).thenReturn(commandMock)
    when(spyCacheHelper.createCommand123(anyString(), anyObject())).thenReturn(commandMock)

    val result: Long = spyCacheHelper.getOrElse("key")(() => 123l)
    println("result = " + result)
    verify(commandMock).execute()

  }

}