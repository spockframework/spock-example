package E02_mocking

import spock.lang.Specification


class PublisherSpec extends Specification {
  def pub = new Publisher()
  def sub1 = Mock(Subscriber)
  def sub2 = Mock(Subscriber)

  def setup() {
    pub.subscribers << sub1 << sub2
  }

  def "delivers events to all subscribers"() {
    when:
    pub.send("event")

    then:
    1 * sub1.receive("event")
    1 * sub2.receive("event")
  }

  def "can cope with misbehaving subscribers"() {
    sub1.receive(_) >> { throw new Exception() }

    when:
    pub.send("event1")
    pub.send("event2")

    then:
    1 * sub2.receive("event1")
    1 * sub2.receive("event2")
  }
}
