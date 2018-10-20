package kinadz.mail.ru;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        String MAZEFILEADRESS = "D:\\Labyrinth\\maze.txt";
        String OUTPUTFILEADRESS = "D:\\Labyrinth\\output.txt";
        try (FileInputStream fileInputStream = new FileInputStream(MAZEFILEADRESS)) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream, Charset.forName("UTF-8")));
            ArrayList<ArrayList<Character>> labyrinth = new ArrayList<ArrayList<Character>>();
            String levelOfLabyrinth = "";
            for (int i = 0; (levelOfLabyrinth = reader.readLine()) != null; i++) {
                labyrinth.add(i, new ArrayList<Character>());
                for (int j = 0; j < levelOfLabyrinth.length(); j++)
                    labyrinth.get(i).add(j, levelOfLabyrinth.charAt(j));
            };
            reader.close();
            int[][] maze = new int[labyrinth.size()][labyrinth.get(0).size()];
            for(int i=0; i<labyrinth.size(); i++) {
                for (int j = 0; j < labyrinth.get(0).size(); j++) {
                    if (labyrinth.get(i).get(j).equals('Y'))
                        maze[i][j] = 0;
                    if (labyrinth.get(i).get(j).equals('N'))
                        maze[i][j] = -1;
                }
            }
            ArrayList<Integer[]> listOfSurroundingCells = new ArrayList<Integer[]>();
            Integer[] startingCell = new Integer[3];
            startingCell[0]=0;// row
            startingCell[1]=0;// column
            startingCell[2]=0;// value
            listOfSurroundingCells.add(startingCell);
            for (int cell=0 ; cell<listOfSurroundingCells.size(); cell++) {
                if(maze[labyrinth.size() - 1][labyrinth.get(0).size() - 1] == 0) {
                    int rowOfSurroundCell = listOfSurroundingCells.get(cell)[0] - 1;
                    int columnOfSurroundCell = listOfSurroundingCells.get(cell)[1] - 1;
                    int numberToCompareForI = listOfSurroundingCells.get(cell)[0] + 2;
                    int numberToCompareForJ = listOfSurroundingCells.get(cell)[1] + 2;

                    if (listOfSurroundingCells.get(cell)[0] == 0) {
                        rowOfSurroundCell = listOfSurroundingCells.get(cell)[0];
                        numberToCompareForI = 2;
                    }

                    if (listOfSurroundingCells.get(cell)[1] == 0) {
                        columnOfSurroundCell = listOfSurroundingCells.get(cell)[1];
                        numberToCompareForJ = 2;
                    }

                    if (listOfSurroundingCells.get(cell)[0] == labyrinth.size() - 1) {
                        rowOfSurroundCell = listOfSurroundingCells.get(cell)[0] - 1;
                        numberToCompareForI = labyrinth.size();
                    }

                    if (listOfSurroundingCells.get(cell)[1] == labyrinth.get(0).size() - 1) {
                        columnOfSurroundCell = listOfSurroundingCells.get(cell)[1] - 1;
                        numberToCompareForJ = labyrinth.get(0).size();
                    }

                    for (int i = rowOfSurroundCell; i < numberToCompareForI; i++) {
                        for (int j = columnOfSurroundCell; j < numberToCompareForJ; j++) {
                            if ((i == listOfSurroundingCells.get(cell)[0]) && (j == listOfSurroundingCells.get(cell)[1]))
                                continue;
                            else {
                                if (maze[i][j] == 0) {
                                    Integer[] nextCell = new Integer[3];
                                    nextCell[0] = i;
                                    nextCell[1] = j;
                                    nextCell[2] = listOfSurroundingCells.get(cell)[2] + 1;
                                    maze[i][j] = nextCell[2];// value of current cell increase for 1
                                    listOfSurroundingCells.add(nextCell);
                                }
                            }
                        }
                    }
                }
                else
                    break;
            }
            System.out.println("List contains "+listOfSurroundingCells.size());
            maze[startingCell[0]][startingCell[1]]=0;
            ArrayList<String> shortcut = new ArrayList<String>();
            Integer[] finishCell = new Integer[3];
            finishCell[0] = labyrinth.size()-1;// row
            finishCell[1] = labyrinth.get(0).size()-1;// column
            finishCell[2] = maze[labyrinth.size()-1][labyrinth.get(0).size()-1];// value
            while(finishCell[2] != 0) {
                int rowOfSurroundCell = finishCell[0] - 1;
                int columnOfSurroundCell = finishCell[1] - 1;
                int numberToCompareForI = finishCell[0] + 2;
                int numberToCompareForJ = finishCell[1] + 2;

                if (finishCell[0] == 0) {
                    rowOfSurroundCell = finishCell[0];
                    numberToCompareForI = 2;
                }

                if (finishCell[1] == 0) {
                    columnOfSurroundCell = finishCell[1];
                    numberToCompareForJ = 2;
                }

                if (finishCell[0] == labyrinth.size() - 1) {
                    rowOfSurroundCell = finishCell[0] - 1;
                    numberToCompareForI = labyrinth.size();
                }

                if (finishCell[1] == labyrinth.get(0).size() - 1) {
                    columnOfSurroundCell = finishCell[1] - 1;
                    numberToCompareForJ = labyrinth.get(0).size();
                }
                boolean keyToExit = false;
                for (int i = rowOfSurroundCell; i < numberToCompareForI; i++) {
                    for (int j = columnOfSurroundCell; j < numberToCompareForJ; j++) {
                        if ((i == finishCell[0]) && (j == finishCell[1]))
                            continue;
                        else{
                            if (finishCell[2]-1==maze[i][j]) {
                                keyToExit = true;
                                String transit = "";
                                if (i < finishCell[0])
                                    transit += "d";
                                if(i > finishCell[0])
                                    transit += "u";
                                else
                                    transit += "";
                                if(j < finishCell[1])
                                    transit += "r";
                                if(j > finishCell[1])
                                    transit += "l";
                                else
                                    transit += "";
                                shortcut.add(transit);
                                finishCell[0] = i;
                                finishCell[1] = j;
                                finishCell[2] = maze[i][j];
                                break;
                            }
                        }
                    }
                    if(keyToExit)
                        break;
                }
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(OUTPUTFILEADRESS, true));
            String string = "The shortest consist of " + maze[labyrinth.size()-1][labyrinth.get(0).size()-1] + " steps: ";
            writer.write(string);
            for(int i=shortcut.size()-1; i >= 0; i--) {
                writer.write(shortcut.get(i)+" ");
            };
            writer.close();
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        };
    }
}
