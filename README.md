# Location-specific game creation environment
This project is associated with the <em>Software Development</em> bachelor thesis of Smilla Maria Dion and Kirstine Berg Jørgensen at the IT University of Copenhagen.

### Problem definition
If you want to create your own online puzzle or mini-game, the current possibilities are limited. One option is to upload a picture on a website, and it will be made into puzzle pieces. Another option is to upload some words, and it makes them into a crossword puzzle. But what if it was possible to create any kind of puzzle? Where you could make buttons, secret messages, passwords and more.

We want to investigate how to create an environment that allows non-programmers to create and customize puzzles.

Goal:
- To have a functioning Android mobile application, where users can create their own location-based mini-games or puzzles using their surroundings, and where other users can solve or play those games by traveling to the specific location.
- To make people look at the world differently, and getting inspired by our surroundings to create small games for the community of users.

## Want to contribute?
You can create your own game or solution components and add them to this project! 

Steps to contribute:

Make sure you have Android Studio downloaded on your computer. (https://developer.android.com/studio)

1. Fork this project, and open it in Android Studio.

2. If you are not yet familiar with the app, run the game in the emulator or on your Android device. Try to create a game with some different components. 

3. In the folder "Components", create a new Java file and give it a unique name.

4. Have it extend GameComponent or SolutionComponent.
    - The constructor of your component should only take an id and call super(id), as required by the superclass - and nothing else.

5. Create an XML layout file that defines how your component will be created by the user (or decide to make it programatically - not recommended).

6. Make sure to implement all required functions: 
    - getCreateView(Context context)
    - getDisplayView(Context context)
    - saveGame()
    - getName()
    - (if SolutionComponent) checkSolution(..., ..., Context context)
  
  
7. Test it!
    - Run the app. Go to 'Create' and find your component (it will be under "+ Component" or "+ Solution" depending on which one you have made).
    - Click it, and it should take you to where the component is created. This is what is returned from the getCreateView() method. 
    - Make sure that it works, and that it does not let you finish if all required information is not given. 
    - If your component is a GameComponent, make sure to add a SolutionComponent so that the game works. 
    - Save your game and play it!

Description of these functions can be found in the documentation in the code, and you can look to existing components for inspiration. If you need further help, more detailed descriptions can be found below:

#### getCreateView(Context context):
This method returns the view where the user creates the component. This will usually consist of a mix of TextView, EditText, Button, CheckBox etc. The view gathers all the information that will later be displayed in the game.
We recommend that you implement this by creating an XML layout file (res/layout). Have a look at other layout files for inspiration. You can even copy another XML file and change the views as needed.

After creating the XML file you inflate it in getCreateView:
```java
View view = LayoutInflater.from(context).inflate(R.layout.[fragment_create_YOUR_component], null, false);
```
    
In your component class, you will likely have created fields for the different views, like: 
```java
private EditText editText;
```
These can only be initialized now, when we have the context and the XML file. Do it like this:
```java
editText = view.findViewById(R.id.edit_text);
```
If you need to change any text, do it here, and then return the view.

#### saveGame():
This method is being called from the UI handling your createView. It makes sure that the user's choices for the creation of your component is stored in the component object.

Check that all required fields are completed and filled out by the user, and if anything is missing, display an error message and return false like this:
```java
Toast toast = Toast.makeText(context, "{field} can't be empty", Toast.LENGTH_SHORT);
toast.setGravity(Gravity.CENTER, 0, 0);
toast.show();
return false;
```
    
If everything is there and correctly filled out, save the data to the class fields and return true, i.e.:
```java
String buttonText = editText.getText().trim().toString();
return true;
```

IMPORTANT: You can NOT save your data as a View. The views will be created when the component is ready to be displayed - in the getDisplayView() method. Views can only be created in the context where they are used. You will have to save your data as strings, integers, arrays etc. In getDisplayView, you will create the actual UI of the component.

#### getDisplayView(Context context):
The component has been created, and now it needs to be displayed in a game.

First, create a layout:
```java
LinearLayout linearLayout = new LinearLayout(context);
```
Now you can code what the component will look like, and assign your saved data to the various buttons, textViews etc, and add them all to the layout:
```java
TextView textView = new TextView(context);
textView.setText(text);
linearLayout.addView(textView);
return linearLayout;
```
If the user should be able to interact with the component, this is the function where you set listeners. They can be made like this:
```java
button.setOnClickListener(view -> myFunction(view, context));
```

#### checkSolution(arg1 a, arg2 b, Context context)
If it is a SolutionComponent you are making, you have to implement a checkSolution() function. This is not part of the abstract super class, as the different components need different arguments.

It is in the getDisplayView method that you determine when checkSolution should be called, and with which arguments. Here is an example with a simple question and input SolutionComponent:
```java
public void checkSolution(View view, Context context) {
        String userSolution = editText.getText().toString().trim();
        if (!isCaseSensitive) {
            userSolution = userSolution.toLowerCase();
            solutionText = solutionText.toLowerCase();
        }

        if (solutionText.equals(userSolution)) {
            setSolved(true);
        } else {
            Toast toast = Toast.makeText(context, "Incorrect", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }
```

If the result of checkSolution is positive, the app will handle the behaviour. If it is incorrect, you should make a toast as shown above, to let the user know it was incorrect.

The most important thing here is to call setSolved(true) when the user has answered correctly. If you don't want to make a separate checkSolution function to handle it like in this example, you can also do it directly in getDisplayView.

### What then?
You have created your component, you have removed any print statements from your code, and made it look nice and clean.
Then you have tested it, and tested it again.
Now you can create a pull request!

### Q&A

Q: Why Java?

A: Our app makes use of Java Reflection. This makes it possible to create a single component class and have it show correctly in the app, without having to hard code anything. Java Reflection finds all classes that extend either GameComponent or SolutionComponent and creates instances of them. 

Q: I want to be able to open the Camera app or Gallery in getCreateView(), how can I do that?

A: This is a bit complicated, as your component class is not an Activity or a Fragment. It is however possible - have a look at the ImageComponent class to see how we did it.
