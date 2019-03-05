package com.example.anthony.realcube2_0;

import java.util.Stack;

public class CubeManipulator
{
    private Stack<Command> undoHistory;
    private Stack<Command> redoHistory;

    public CubeManipulator()
    {
        undoHistory = new Stack<Command>();
        redoHistory = new Stack<Command>();
    }

    public void manipulateCube(Command c)
    {
        undoHistory.push(c);
        c.execute();
    }

    public void clearHistory()
    {
        undoHistory = new Stack<Command>();
        redoHistory = new Stack<Command>();
    }

    public Command undo()
    {
        if (!undoHistory.isEmpty())
        {
            redoHistory.push(undoHistory.peek());
            undoHistory.peek().unexecute();
            return undoHistory.pop();
        }
        return null;
    }

    public Command redo()
    {
        if (!redoHistory.isEmpty())
        {
            undoHistory.push(redoHistory.peek());
            redoHistory.peek().execute();
            return redoHistory.pop();
        }
        return null;
    }
}
