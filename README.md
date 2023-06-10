## (UI NOT FINALIZED)

# BookLibraryApp 

<img width="1920" alt="Screenshot 2023-06-11 at 01 42 37" src="https://github.com/krooldonutz/BookLibraryApp/assets/26025886/bf4c0802-1a36-45a1-a765-9f7bfe4afcbf">

This repository contains the code for an Android application developed during my Semester as a semester long project. The application is a book library app that allows users to manage their book collection. It uses Firebase Realtime Database for data storage and retrieval.

# Features

- Add a book: Users can add a new book to their collection by entering the book's details such as title, author, ISBN, description, and price.
- View book collection: Users can view their entire book collection in a RecyclerView.
- Delete books: Users can remove books from their collection.
- Undo feature: When a book is deleted, users have the option to undo the deletion.
- SMS integration: The app can receive SMS messages with book details and automatically populate the corresponding fields.

 # Dependencies

The following dependencies are used in this project:

- androidx.appcompat:appcompat:1.3.1
- androidx.recyclerview:recyclerview:1.2.1
- com.google.android.material:material:1.4.0
- androidx.lifecycle:lifecycle-viewmodel:2.3.1
- com.google.firebase:firebase-database:20.0.1
- Make sure to sync the project with Gradle files to download these dependencies.

# Configuration

To use Firebase Realtime Database in this app, you need to set up a Firebase project and obtain the necessary configuration files.

- Go to the Firebase Console.
- Create a new project or select an existing project.
- Follow the instructions to add an Android app to your project and download the google-services.json file.
- Place the google-services.json file in the app/ directory of the project.
- Open the MainActivity.java file and uncomment the following line:
```
// mRef = FirebaseDatabase.getInstance("https://your-project-id.firebaseio.com").getReference("Book/books");

```
- Replace your-project-id with your Firebase project ID.

- Build and run the app.

# Usage

Launch the app on an Android device or emulator. You can perform the following actions within the app:

- Add a book: Enter the book details in the provided fields and click the "Add" button.
- Delete a book: Swipe left on a book item in the collection.
- Undo deletion: After deleting a book, an option to undo the deletion will appear.
- Load last saved book details: Click the "Load Last" button to load the book details from the last saved state.
- Clear fields: Click the "Clear" button to clear all the input fields.
- Double the price: Click the "Double Price" button to multiply the current price by 2.
- Change ISBN: Click the "ISBN Change" button to change the ISBN to a predefined value.
- Next activity: Click the "Next Activity" button to navigate to another activity.



