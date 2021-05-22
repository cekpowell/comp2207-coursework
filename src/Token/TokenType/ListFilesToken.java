package Token.TokenType;

import java.util.ArrayList;

import Token.Token;

/**
 * Token for ...
 * 
 * Syntax: 
 */
public class ListFilesToken extends Token{
    public ArrayList<String> filenames;

    public ListFilesToken(String message, ArrayList<String> filenames){
        this.message = message;
        this.filenames = filenames;
    }
}