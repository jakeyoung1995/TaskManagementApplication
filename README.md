# TaskManagementApplication
## 1.) Target 

We will be creating a Task Management Application that will help users manage their tasks, set deadlines, prioritize them, and track their progress. The target audience is individuals, teams, or organizations looking for an efficient way to organize their work and increase productivity.

## 2.) Design Layouts and Controls
**Design Layouts:**
GridPane because I anticipate using a dashboard view where we can display tasks in a grid or tabular format with each task occupying a specific cell in the grid. 
VBox and HBox will be used to arrange separate UI elements in a vertical or horizontal orientation. This may be useful when creating task details and task creation layouts, where you can stack UI controls vertically or horizontally. 
BorderPane may be used for placing different task information in different regions on the screen. 
StackPane will likely be used for situations where I want to overlap something or create a pop-up dialog. 
**UI Controls: **
List View will be used to display a list of tasks in the dashboard layout which will allow for easy scrolling, selection, and interaction with individual tasks. 
Progress Bars will be used to show completion progress for tasks. 
Text fields will be used for inputting or modifying task related information. 
Date Pickers will be used for selecting due dates while creating or editing tasks in a calendar style interface. 
Buttons will be used in various different ways to submit, save, delete, edit etc. 

## 3.) Define Scenes
**Dashboard Scene**
Purpose: Provide an overview of tasks and their status
Content: ListView of tasks, progress bars, filters
Interactions: Selecting a task displays its details in the task details scene
**Task Details Scene**
Purpose: Display detailed information about a selected task. 
Content: Text fields, date picker, priority selection, buttons
Interactions: Editing task information, saving changes, or returning to the dashboard scene. 
**Task Creation Scene: **
Purpose: Allow users to create new tasks.
Content: Input fields for task name, description, date picker, buttons. 
Interactions: Entering task information, saving the task, or canceling the creation process. 
**Statistics Scene: **
Purpose: Provide statistics or insights about task completion
Content: Charts, graphs, or visual representations of task-related data. 
Interactions: Navigating back to the dashboard or exploring detailed task analytics. 

## 4.) Justify Design Choices
**Design Justification:** There are essentially 2 main screens: The dashboard and the statistics scene. These will be the scenes which users will be using primarily to display information that will be useful to the user. The Task Details Scene and the Task Creation Scenes are where users will input data that will affect the 2 main scenes. There is functionality on each scene that will allow the users to move seamlessly between all screens with intuitiveness. On the Dash, you can change to any of the other 3 scenes. The 2 user input scenes will take the user back to the dashboard when they have completed their inputs. And the statistics scene will allow the user to edit completed tasks and return back to the dashboard. 
**Challenges:** I anticipate a few challenges when I implement this application. These issues may include handling task data persistence in the statistics scene, implementing data validation on the user input scenes, and incorporating responsive design for different screen sizes. Additionally, overall user experience, making sure all of the different buttons and input areas are intuitive for the user. 

## 5.) Reflection
**Importance of Application:** It is immensely important to anticipate users’ needs, preferences, and behaviors and create an interface that is easy to navigate, visually appealing, and efficient in achieving tasks. The application must be usable which will be achieved through data validation. It must be appealing to the user as well which will provide comfort for the user as well as intuitiveness in design to lead the user seamlessly through the application. I hope by making this application, it will provide the user with an efficient workflow in organizing and prioritizing their tasks. I also want this application to be accessible to users across various different device sizes so it will need to be responsive. 
**Trends:** In the current landscape, users are seeking intuitive and visually appealing applications that help them organize their work, life, or any other task that they are unable to keep track of simply in their head. This app will be simple in function in a system where sometimes, less is more. A lot of applications like this are very complicated and almost make it more work to organize their tasks than if they had just written it down on a piece of paper. This will be a responsive, simple, and dedicated task manager. 
