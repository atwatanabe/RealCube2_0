package com.example.anthony.realcube2_0;

public class LeftCommand implements Command
{
    private Cube3x3 cube;
    private boolean inverted;

    public LeftCommand(Cube3x3 c, boolean invert)
    {
        cube = c;
        inverted = invert;
    }

    public void execute()
    {
        cube.left(inverted);
    }

    public void unexecute()
    {
        cube.left(!inverted);
    }

    @Override
    public String toString()
    {
        return "Left " + inverted;
    }
}
