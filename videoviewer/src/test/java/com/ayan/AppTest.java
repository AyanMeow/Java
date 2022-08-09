package com.ayan;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

import com.ayan.user.UserView;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        //File file=new File("test.txt");
        String[] start={" "," "};
        UserView.main(start);
        assertTrue( true );
    }
}
