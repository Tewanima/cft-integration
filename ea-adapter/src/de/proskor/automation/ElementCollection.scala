package de.proskor.automation

import cli.EA.ICollection
import cli.EA.IElement

class ElementCollection(peer: ICollection) extends Collection[Element](peer) {
  type PeerType = IElement
  override def create(peer: IElement): Element = new Element(peer)
  override def update(peer: IElement): Unit = peer.Update()
}