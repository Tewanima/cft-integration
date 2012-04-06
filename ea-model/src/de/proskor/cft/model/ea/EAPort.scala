package de.proskor.cft.model.ea
import de.proskor.cft.model.ea.peers._
import de.proskor.cft.model.Port
import de.proskor.cft.model.Source

private class EAPort(peer: ConnectedPeer) extends EAElement(peer) with Port {
  def input: Option[Source] = {
    val connectedElements = peer.connectedElements.map(EAFactory.create).asInstanceOf[Set[Source]]
    if (connectedElements.isEmpty)
      None
    else
      Some(connectedElements.head)
  }
  def add(input: Source) {
    require(input.isInstanceOf[EAElement])
    peer.connect(input.asInstanceOf[EAElement].peer)
  }
  def remove(input: Source) {
    require(input.isInstanceOf[EAElement])
    peer.disconnect(input.asInstanceOf[EAElement].peer)
  }
}