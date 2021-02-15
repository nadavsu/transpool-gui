![Title and slogan cropped](https://user-images.githubusercontent.com/53123142/81192618-24fa4e80-8fc3-11ea-9d8e-809b7ee11ac5.png)
TransPool, the ride sharing application. With TransPool, it has never been easier finding someone to ride with for work. Whether you are fed up with public transportation and looking for a way to reduce your commute time or if you want to cover your automobile fees each month. TransPool is the application for you!


# Application Instructions

## Getting Started

When opening TransPool the first thing you need to do is load a file:

1. Choose File -> Open... from the menu bar on the top left hand side of the window

2. Select your XML file and click on Open.

3. On the bottom left hand side, you could see the status of the program while it is loading the file.

4. Once the TransPool finished loading the file, the buttons in the  action bar (left hand side bar) should be enabled. Also, the live map should and the api should be shown on the screen like the image below.

5. Should an error occur, the label on the bottom right will let you know what went wrong.

    ![mainscreen](https://user-images.githubusercontent.com/53123142/85154627-5c197c00-b260-11ea-9f02-d0c4a69befe0.png)


## The Action Bar

The tab pane on the left hand side of the window contains 4 different tabs where actions can be done.

**Note: By default, only the first two tabs are shown. You can show the 2 other tabs by scrolling up and down on your mouse while hovering over the tabs, or by dragging the tabs left and right**

### Trip Request

Input your name, source stop (case sensitive), destination stop (case sensitive) the day of departure, and the time of departure.

Clicking on the clock icon will open an easy way of inputting the time.

To select time using the time selector:

1. Choose your hour
2. Click on the minutes on the top right (50 in the image)
3. Choose your minutes.

Arrival time option is disabled. This feature is not yet done.

When done click "Add request".



### Trip Offer

1. Enter your details and time like in the Trip Request seciton above
2. To add you route -
   1. Choose your first station from the combobox with the stations
   2. Click on the `+` button to add the station to your route
   3. Any undesired stations can be removed by choosing the station and clicking on the `-` button.
   4. The last station in the list will be the last station in your route.



### Matching

1. Choose the ride you want to match from the combobox
2. Enter the number of results - a number bigger than 0
3. Click search.
4. Choose the desired route from the list (if any) and click match. A match will be created and can be viewed in the information bar on the right hand side of the application.
5. If you want to add another match for another trip, the form must be cleared first. This is done by clicking on the clear button.



### Feedback

1. Choose who you want to feedback as
2. Click on Show Drivers to view the matched requests drivers
3. Select the driver you want to add feedback to from the list, add rating from the combobox and write a comment if you want.
4. When done, click on add feedback and the feedback should be shown in the trip offer's feedback section inside the information bar.



## Information Bar

The tab pane on the right hand side of the application displays the necessary information of each trip offer/request matched or unmatched. There are 4 tabs located on the leftmost side of the window



1. Trip Offers - displays all trip offers on system, irregardless of time.
2. Current Offers - displays all trip offers at the current selected time in the time selector on the bottom of the window.
3. Requests - displays all trip requests on the system, irregardless of time.
4. Matches - Shows all matched trips on the system, irregardless of time.

Each tab contains a list of **cards**. Each card displays the api of each offer/request/whatever.



### Trip Offer Card

![Screen Shot 2020-06-19 at 0 05 04](https://user-images.githubusercontent.com/53123142/85154731-7e12fe80-b260-11ea-92e9-5fdcd57ee9e5.png)

- Name and rating are displayed at the top\
- The number below the rating is the TripOffer ID
- The schedule of the Trip Offer is displayed below the ID, and below that the route (read from left to right)
- The lists below show the matched riders - who gets on/off when/where - and the feedbacks from the matched riders.
- The matched riders are shown irregardless of time. To view the matched drivers with respect to time you must click on the current stop at the right time in the map



### Trip Request Card

![Screen Shot 2020-06-19 at 0 13 56](https://user-images.githubusercontent.com/53123142/85154767-8bc88400-b260-11ea-8e9b-1c3c8733daae.png)


### Matched Request Card

- Contains the route - where he gets on and off and when.
- Contains route summary.

![Screen Shot 2020-06-19 at 0 14 59](https://user-images.githubusercontent.com/53123142/85154817-a1d64480-b260-11ea-8924-ab37b67b2ae9.png)


### Current Offers

- Displays the current trip offers happening on the current chosen time. Updates as you move through time.



## Live Map & Time Selector

The live map displays live information about the rides and their current stops at the current selected time. 

### Time Selector

The time selector allows the user to move through time and see where each ride is. The user can choose to jump by 5 minutes, 30 minutes, 1 hour, 2 hours or a full day. This is done by clicking on the combobox on the bottom containing the intervals. To the right of that, 2 labels hold the current day and current time.



### Live Map

- Each pink dot is a stop
- If a stop turns blue, then there is a carpool at that stop right now.
- You can view the name of each stop by hovering over the blue dot and waiting for the tooltip.
- Above each stop there's a number containing the number of rides currently at that stop.
- You can view who is currently at this stop by clicking on the stop.

![Screen Shot 2020-06-19 at 0 25 21](https://user-images.githubusercontent.com/53123142/85154880-b61a4180-b260-11ea-8ae4-be14d484ca03.png)
