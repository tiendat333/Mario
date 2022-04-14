/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mario.dto;

/**
 *
 * @author tolet
 */
public class Leader {
    public String Name, Score;

    public Leader(){
        Name = "";
        Score = "";
    }
    
    public Leader(String Name, String Score) {
        this.Name = Name;
        this.Score = Score;
    }

    
    
    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getScore() {
        return Score;
    }

    public void setScore(String Score) {
        this.Score = Score;
    }
    
    
}

