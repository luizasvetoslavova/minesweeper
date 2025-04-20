Presentation: https://drive.google.com/file/d/1iCSYad-_eezXRqWwIoRPxLJHjN336qzJ/view?usp=sharing
Documentation: https://drive.google.com/file/d/1CLKI1wqaIS3KNE_EfYzTPorH87V2S8Fn/view?usp=sharing
<h2>
  Minesweeper
</h2>
Minesweeper is a 90's video game, originally created by Microsoft. The graph table resembles a board where each square contians a hidden element that could be either a <b>mine</b>,
an <b>empty cell</b> or a <b>digit</b>. Every digit represents the number of mines adjacent to it. If a cell is empty (grey), it means that there are no mines adjacent to it. If an empty cell gets opened, all empty cells adjacent to it open all at once.<br><br>

The objective of the game is to find out where all the mines are and put a flag on each of them without actually _opening_ any. If you do open a cell and 'step' on a mine, the game ends and you lose.
All digit squares need to be opened in order for you to win. <br> <br>
Tables of different difficulty contain different amount of squares and mines.
You can start by clicking at any random square. Your first click will not be on a mine. <br>
Left click to open a cell. Right click to flag.

<h3>
  Levels:
</h3>
Easy: <b>9 x 9</b> matrix, <b>15</b> mines. <br>
Medium: <b>16 x 16</b> matrix, <b>40</b> mines. <br>
Hard: <b>16 x 30</b> matrix, <b>85</b> mines. <br>
Expert: <b>23 x 34</b> matrix, <b>149</b> mines. <br>
Custom: Anything you choose from <b>3 up to 50</b> squares, <b>20%</b> of which are mines. <br> 

<h3>
  Scores:
</h3>
The program stores your highest score for each level - best performance time and click count.
As you update your score by taking less time or using less clicks to finish a game, it gets 
updated on the 'scores' page section, and is stored in a database. <br><br>
As you hit a new score you see
your old one, and as you win but don't beat your own best score you see a comparison 
between your current performance and your best score. By default all scores are zero and they 
get automatically updated as you win your first game. <br> 

<h3>
  UI/UX:
</h3>
The rules are available on the home page. You can choose any level to start with.
The game could be reset both when one is done playing and before that. <br><br> When there is a win, you can move on to the next
level directly from the page you're on at the moment. If you're impatient, you can navigate to a higher or 
a custom level from the home page. It's way more fun to work your way out to expert
level, though!
Unlike next level buttons, previous level navigation is always available. <br><br>
You can navigate to the score page where you're be able to see all scores for each level that you've set so far. You've got the option to reset your scores in order to start setting them from scratch, just like when you first downloaded the game. <br>
