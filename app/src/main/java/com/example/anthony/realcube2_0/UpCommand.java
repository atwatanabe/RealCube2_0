package com.example.anthony.realcube2_0;

public class UpCommand implements Command
{
    private Cube3x3 cube;
    private boolean inverted;

    public UpCommand(Cube3x3 c, boolean invert)
    {
        cube = c;
        inverted = invert;
    }

    public void execute()
    {
        cube.up(inverted);
    }

    public void unexecute()
    {
        cube.up(!inverted);
    }

    @Override
    public String toString()
    {
        return "Up " + inverted;
    }
}
