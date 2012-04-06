package de.proskor.ea
import cli.EA.IRepository
import de.proskor.cft.model.ea.EAFactory
import de.proskor.cft.model.Factory

trait Adapter extends Extension {
  var outputReady = false
  var repositoryPeer: cli.EA.IRepository = null

  val MainMenu = "-CFT Extension"
  val TestMenuItem = "Test"
  val RunTestsMenuItem = "Run Tests"
  val MergeMenuItem = "Merge..."

  def EA_OnPostInitialized(repository: IRepository) {
    Factory.default = EAFactory
    repositoryPeer = repository
    de.proskor.automation.Repository.peer = repository
  //  EAFactory.repositoryPeer = repository
    start()
  }

  def EA_Disconnect {
    stop()
  }

  def EA_GetMenuItems(repository: IRepository, location: String, menuName: String): Any = menuName match {
    case "" => MainMenu
    case MainMenu => Array(TestMenuItem, RunTestsMenuItem, MergeMenuItem)
  }

  def EA_MenuClick(repository: IRepository, menuName: String, itemName: String): Unit = itemName match {
    case TestMenuItem => test()
    case RunTestsMenuItem => runTests()
    case MergeMenuItem => merge()
    case _ =>
  }

  def write(text: String) {
    if (!outputReady) {
      repositoryPeer.CreateOutputTab("CFT Extension")
      repositoryPeer.EnsureOutputVisible("CFT Extension")
      outputReady = true
    }
    repositoryPeer.WriteOutput("CFT Extension", text, 1)
  }
}