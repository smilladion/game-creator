# Location-specific game creation environment
This project is associated with the <em>Software Development</em> bachelor thesis of Smilla Maria Dion and Kirstine Berg Jørgensen at the IT University of Copenhagen.

### Problem definition
If you want to create your own online puzzle or mini-game, the current possibilities are limited. One option is to upload a picture on a website, and it will be made into puzzle pieces. Another option is to upload some words, and it makes them into a crossword puzzle. But what if it was possible to create any kind of puzzle? Where you could make buttons, secret messages, passwords and more.

We want to investigate how to create an environment that allows non-programmers to create and customize puzzles.

Goal:
- To have a functioning Android mobile application, where users can create their own location-based mini-games or puzzles using their surroundings, and where other users can solve or play those games by traveling to the specific location.
- To make people look at the world differently, and getting inspired by our surroundings to create small games for the community of users.

## Want to contribute?
You can create your own Game - or Solution Components and add them to this project! 

Steps to contribute:

Make sure you have Android Studio downloaded on your computer. 

1. Clone this project, and open it in Android Studio. 
2. In the folder "Components", create a new Component and give at a unique name.
3. Have it extend GameComponent or SolutionComponent.

4. Create an XML-layout file for how your component will be created. (Or decide to make it programatically - not recommended)

5. Make sure to implement all required functions: 
    - getCreateView()
    - getDisplayView()
    - saveGame()
    - getName()
  
  
6. Test it!
    - Run the app. Go to 'Create', and find your component (It will be under "+ Component" or "+ Solution" depending on which one you have made.)
    - Click it, and it should take you to where the Component is created. This is what is returned from getCreateView. 
    - Make sure that it works, and that it does not let you finish if all required information is not given. 
    - If your Component is a GameComponent, make sure to add a SolutionComponent so that the game works. 
    - Save your game and play it!

Description of these functions can be found in the documentation in the code, and you can look to existing components for inspiration. If you need further help, more detailed descriptions can be found below:

#### getCreateView():
This is the page where the user creates the Component. This will usually consist of a mix of TextView, EditText, Button, CheckBox etc. 
It is to gather all the information that will later be displayed in the game.
We recommend that you do this by creating a xml-layout fil. (res/layout) Have a look at other layout files for inspiration. You can even copy a whole other xml-file, and change the Views as needed.

After creating the xml file, you inflate it in the getCreateView:
    View view = LayoutInflater.from(context).inflate(R.layout.[fragment_create_YOUR_component], null, false);
    
In your class, you will have created fields for the different Views, like: 
    EditText editText;
These can only be initialised now, when we have the context and the xml file. Do it like this:
    editText = view.findViewById(R.id.edit_text);
    
If you need to change any text, do it here, and then return the view.

#### saveGame():
This method is being called from the UI handling your createView. 
Check that all required fields are completed, and if anything is missing
    return false

If everything is there, save the data to the class fields., ie:
    String buttonText = editText.getText().trim().toString();


