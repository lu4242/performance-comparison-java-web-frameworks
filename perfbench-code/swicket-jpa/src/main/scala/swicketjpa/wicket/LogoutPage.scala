package swicketjpa.wicket

import org.apache.wicket.RestartResponseException
import org.apache.wicket.markup.html.WebPage

class LogoutPage extends WebPage {
  getSession().invalidate()
  throw new RestartResponseException(classOf[HomePage])
}
