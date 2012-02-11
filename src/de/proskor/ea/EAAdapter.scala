package de.proskor.ea
import cli.EA._
import de.proskor.ea.model._
import de.proskor.Extension

trait EAAdapter {
  this: Extension =>

  val MainMenu = "-CFT Integration"
  val MergeMenuItem = "Merge..."
  val DiagramMenuItem = "Create Diagram"
  val FailureModesMenuItem = "Show Failure Modes"
  val PrintIdMenuItem = "Print Element Id"

  def EA_Connect(repository: IRepository) = {
    start(new EARepository(repository))
    ""
  }

  def EA_Disconnect() = close()

  def EA_GetMenuItems(repository: IRepository, location: String, menuName: String): Any = menuName match {
    case "" => MainMenu
    case MainMenu => Array(MergeMenuItem, DiagramMenuItem, FailureModesMenuItem, PrintIdMenuItem)
  }

  def EA_MenuClick(repository: IRepository, menuName: String, itemName: String) = itemName match {
    case MergeMenuItem => merge(new EARepository(repository))
    case DiagramMenuItem => createDiagram(new EARepository(repository))
    case FailureModesMenuItem => failureModes(new EARepository(repository))
    case PrintIdMenuItem => printId(new EARepository(repository))
    case _ =>
  }
}