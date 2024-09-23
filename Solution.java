import java.util.*;
import java.io.*;
import java.math.*;

class Maze implements Cloneable {
    public int         h;
    public int         w;
    public String []   map;

    public Maze(int _h, int _w) {
        h = _h;
        w = _w;
        map = new String[h];
    }

    public Object   clone() {
        try {
            Maze temp = (Maze)super.clone();
            temp.map = (String [])map.clone();
            // for (int i = 0; i < h; i++) {
            //     temp.map[i] = new String(map[i]);
            // }
            return temp;
        } catch (CloneNotSupportedException ex) {
            throw new InternalError(ex.toString());
        }
    }

    public void PrintDebug() {
        for (int i = 0; i < h; i++) {
            System.err.println(map[i]);
        }
    }

    public void Print() {
        for (int i = 0; i < h; i++) {
            System.out.println(map[i]);
        }
    }

    private char getPos(int pos) {
        if (pos <= 9) {
            return (char)(pos + '0');
        }
        return ((char)(pos - 10 + 'A'));
    }

    private void virus(int _i, int _j, int pos) {
        // System.err.println("info virus i:" + _i + ", j:" + _j + ", pos: " + pos);
        // System.err.println("info1 map[i]:" + this.map[_i]);
        // set this one
        
        StringBuilder tempstr = new StringBuilder(map[_i]);
        char ch = getPos(pos);
        tempstr.setCharAt(_j, ch);
        this.map[_i] = new String(tempstr.toString());
        // System.err.println("info2 map[i]:" + this.map[_i]);

        // check the one up
        if (_i != 0) {
            char temp = this.map[_i - 1].charAt(_j);
            switch (temp) {
                case '#':
                    break ;
                
                case '.':
                    StringBuilder string = new StringBuilder(map[_i - 1]);
                    char c = getPos(pos);
                    string.setCharAt(_j, c);
                    this.map[_i - 1] = new String(string.toString());
                    virus(_i - 1, _j, pos + 1);
                    break ;
                    
                default: 
                    break ;
            }
        }

        // check the one down
        if (_i < h) {
            char temp = this.map[_i + 1].charAt(_j);
            switch (temp) {
                case '#':
                    break ;
                
                case '.' :
                    StringBuilder string = new StringBuilder(map[_i + 1]);
                    char c = getPos(pos);
                    string.setCharAt(_j, c);
                    this.map[_i + 1] = new String(string.toString());
                    virus(_i + 1, _j, pos + 1);
                    break ;

                default:
                    break ;
            }
        }

        // check the one left
        if (_j != 0) {
            char temp = this.map[_i].charAt(_j - 1);
            switch (temp) {
                case '#':
                    break ;
                
                case '.':
                    StringBuilder string = new StringBuilder(map[_i]);
                    char c = getPos(pos);
                    string.setCharAt(_j - 1, c);
                    this.map[_i] = new String(string.toString());
                    virus(_i, _j - 1, pos + 1);
                    break ;

                default:
                    break ;
            }
        }

        // check the one right
        if (_j < w - 1) {
            char temp = this.map[_i].charAt(_j + 1);
            switch (temp) {
                case '#':
                    break ;
                
                case '.':
                    StringBuilder string = new StringBuilder(map[_i]);
                    char c = getPos(pos);
                    string.setCharAt(_j + 1, c);
                    this.map[_i] = new String(string.toString());
                    virus(_i, _j + 1, pos + 1);
                    break ;

                default:
                    break ;
            }
        }
    }

    public void activateVirus(Maze input) {
        for(int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                if (input.map[i].charAt(j) == 'S')
                    virus(i, j, 0);
            }
        }
    }
}

class Solution {
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int w = in.nextInt();   //width
        int h = in.nextInt();   //height

        Maze input, solution;
        input = new Maze(h, w);
        solution = new Maze(h, w);
        

        if (in.hasNextLine()) {
            in.nextLine();
        }
        for (int i = 0; i < h; i++) {
            String ROW = in.nextLine();
            input.map[i] = ROW;
        }

        solution = (Maze)input.clone();
        // System.err.println("solution map[0]: " + solution.map[0]);

        solution.activateVirus(input);

        System.err.println("input: ");
        input.PrintDebug();
        System.err.println("\nsolution: ");
        solution.Print();

        // for (int i = 0; i < h; i++) {
        //     System.err.println("input: " + i + " map[i]: " + map[i]);
        // }
    }
}