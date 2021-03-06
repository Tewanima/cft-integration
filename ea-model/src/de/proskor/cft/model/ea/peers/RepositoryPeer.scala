package de.proskor.cft.model.ea.peers

import de.proskor.cft.model.ea.peers.impl.EARepositoryPeer

trait RepositoryPeer extends Peer {
  def packages: Set[PackagePeer]
  def add(element: PackagePeer): PackagePeer
  def remove(element: PackagePeer): PackagePeer
}

object RepositoryPeer {
  def instance: RepositoryPeer = new EARepositoryPeer(de.proskor.automation.Repository.instance)
}