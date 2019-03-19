package com.example.anthony.realcube2_0;

public class RightCommand implements Command
{
    private Cube3x3x3 cube;
    private boolean inverted;

    public RightCommand(Cube3x3x3 c, boolean invert)
    {
        cube = c;
        inverted = invert;
    }

    public void execute()
    {
        cube.right(inverted);
    }

    public void unexecute()
    {
        cube.right(!inverted);
    }

    @Override
    public String toString()
    {
        return "Right " + inverted;
    }
}
