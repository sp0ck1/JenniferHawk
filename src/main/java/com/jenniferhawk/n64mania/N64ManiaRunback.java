package com.jenniferhawk.n64mania;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class N64ManiaRunback {

    String game;
    String winner;
    String comment;
    String commenter;

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCommenter() {
        return commenter;
    }

    public void setCommenter(String commenter) {
        this.commenter = commenter;
    }




}
