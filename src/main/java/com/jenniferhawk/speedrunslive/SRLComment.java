package com.jenniferhawk.speedrunslive;

import lombok.NonNull;

public class SRLComment {

    String game;
    String comment;
    String commenter;

    @NonNull
    public SRLComment(String game, String comment, String commenter) {
        setGame(game);
        setComment(comment);
        setCommenter(commenter);
    }

    public String getGame() {
        return game;
    }

    private void setGame(String game) {
        this.game = game;
    }

    public String getCommentPhrase() {
        return comment;
    }

    private void setComment(String comment) {
        this.comment = comment;
    }

    public String getCommenter() {
        return commenter;
    }

    private void setCommenter(String commenter) {
        this.commenter = commenter;
    }


}


