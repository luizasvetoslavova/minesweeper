<h2>
  Minesweeper
</h2>
Minesweeper is a 90's video game originally created by Microsoft. The given graph table resembles a board where each square contians a hidden element that could be either a **mine**,
an **empty cell** or a **digit**. Every digit represents the number of mines adjacent to it. If a cell is empty (grey), it means that there are no mines adjacent to it. If an empty cell gets opened, all empty cells adjacent to it, and each other, open all at once.

The objective of the game is to find out where all the mines are and put a flag on each of them without actually _opening_ any. If you do open the cell and 'step' on the mine, the game ends and you lose.
All digit squares need to be opened in order for you to win as well. <br> <br>
Tables of different difficulty levels contain different amount of squares and mines.
You can start by clicking at any random square. <br>
Click on a cell to open it. Right click to put a flag on it.

<h3>
  Levels
</h3>
Easy - **9 x 9** matrix, **15** mines. <br>
Medium - **16 x 16** matrix, **40** mines. <br>
Hard - **16 x 30** matrix, **85** mines. <br>
Expert - **23 x 34** matrix, **149** mines. <br>
Custom - Anything you choose from 3 up to 50 squares, 20% of which are mines. <br> 

<h3>
  Scores
</h3>
The program stores your highest score for each level - best performance time and clicks.
As you update your score by taking less time or using less clicks to finish a game, it gets 
updated on the 'scores' page section as well, and stored in a database. As you hit a new score you're being shown
your old one, and as you win but can't beat your own best score you see a comparison 
between your current score and your best one. By default all scores are zero, but they 
get automatically updated as you win your first game for the certain level. <br> 

<h3>
  UI/UX
</h3>
As stated above, you can choose any level to start with from home page, which also exposes the rules.
The game could be reset both when one is done playing and before that. When there is a win, you can move on to the next
level directly from the page you're on at the moment. If you're impatient, though, you can navigate to a higher or 
a custom level from the home page that you have access to all the time. It's way more fun to work your way out to expert
level, though! <br> 
So, unlike next level buttons, previous level navigation is always available. <br>
You can navigate to score page as well, where you're be able to see all scores for each level that you've set so far. You've got the option to reset your scores in order to be able to start setting them from scratch, just like when you downloaded the game first. <br>

<h3>
  To do:
</h3>
- Random shaped tables <br>
- Timer visualization and flag counter <br>
- Improved UI design <br>
- Faster opening of the cells <br>
- Save scores in JAR file <br>
<br> <br>

> <a href="https://github.com/luizasvetoslavova/minesweeper/raw/main/out/artifacts/minesweeper_jar/minesweeper.jar">**DOWNLOAD JAR** (scores not saving properly)</a>
