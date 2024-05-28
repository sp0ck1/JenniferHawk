package com.jenniferhawk.n64mania;

import lombok.Data;


@Data
public class N64ManiaUpdateResult {

    // Update would show the list of errors. Are you allowed to put logic in these files? I bet you could, I should try, so I could check if error list is empty

    String[] errors; // The list of games that did not compute

    
}
