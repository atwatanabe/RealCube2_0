package com.example.anthony.realcube2_0;

import android.util.Log;

import java.util.Map;
import java.util.TreeMap;

public class Cube3x3x3
{
    protected Map<Side, Color[][]> sides;

    public Cube3x3x3()
    {
        sides = new TreeMap<Side, Color[][]>();
        initializeCube();
    }

    public enum Color
    {
        White, Blue, Red, Yellow, Green, Orange
    }

    public enum Side
    {
        Up, Down, Left, Right, Front, Back
    }



    public void initializeCube()
    {
        Color[][] up = new Color[][]
        {
            {Color.White, Color.White, Color.White},
            {Color.White, Color.White, Color.White},
            {Color.White, Color.White, Color.White}
        };
        Color[][] down = new Color[][]
        {
            {Color.Yellow, Color.Yellow, Color.Yellow},
            {Color.Yellow, Color.Yellow, Color.Yellow},
            {Color.Yellow, Color.Yellow, Color.Yellow}
        };
        Color[][] left = new Color[][]
        {
            {Color.Red, Color.Red, Color.Red},
            {Color.Red, Color.Red, Color.Red},
            {Color.Red, Color.Red, Color.Red}
        };
        Color[][] right = new Color[][]
        {
            {Color.Orange, Color.Orange, Color.Orange},
            {Color.Orange, Color.Orange, Color.Orange},
            {Color.Orange, Color.Orange, Color.Orange}
        };
        Color[][] front = new Color[][]
        {
            {Color.Blue, Color.Blue, Color.Blue},
            {Color.Blue, Color.Blue, Color.Blue},
            {Color.Blue, Color.Blue, Color.Blue}
        };
        Color[][] back = new Color[][]
        {
            {Color.Green, Color.Green, Color.Green},
            {Color.Green, Color.Green, Color.Green},
            {Color.Green, Color.Green, Color.Green}
        };

        sides.put(Side.Up, up);
        sides.put(Side.Down, down);
        sides.put(Side.Left, left);
        sides.put(Side.Right, right);
        sides.put(Side.Front, front);
        sides.put(Side.Back, back);
    }

    public Color[][] getFront()
    {
        return sides.get(Side.Front);
    }


    public void up(boolean invert)
    {
        Log.i("Cube move", "up " + invert);
        Color[][] up = sides.get(Side.Up);
        Color tempCorner = up[0][0];
        Color tempEdge = up[0][1];

        Color[][] front = sides.get(Side.Front);
        Color[][] left = sides.get(Side.Left);
        Color[][] back = sides.get(Side.Back);
        Color[][] right = sides.get(Side.Right);

        if (!invert)
        {
            up[0][0] = up[2][0];
            up[2][0] = up[2][2];
            up[2][2] = up[0][2];
            up[0][2] = tempCorner;

            up[0][1] = up[1][0];
            up[1][0] = up[2][1];
            up[2][1] = up[1][2];
            up[0][1] = tempEdge;

            for (int i = 0; i < 3; ++i)
            {
                Color temp = front[0][i];
                front[0][i] = right[0][i];
                right[0][i] = back[0][i];
                back[0][i] = left[0][i];
                left[0][i] = temp;
            }
        }
        else
        {
            up[0][0] = up[0][2];
            up[0][2] = up[2][2];
            up[2][2] = up[2][0];
            up[2][0] = tempCorner;

            up[0][1] = up[1][2];
            up[1][2] = up[2][1];
            up[2][1] = up[1][0];
            up[1][0] = tempEdge;

            for (int i = 0; i < 3; ++i)
            {
                Color temp = front[0][i];
                front[0][i] = left[0][i];
                left[0][i] = back[0][i];
                back[0][i] = right[0][i];
                right[0][i] = temp;
            }
        }
    }

    public void down(boolean invert)
    {
        Log.i("Cube move", "down " + invert);
        Color[][] down = sides.get(Side.Down);
        Color tempCorner = down[0][0];
        Color tempEdge = down[0][1];

        Color[][] front = sides.get(Side.Front);
        Color[][] left = sides.get(Side.Left);
        Color[][] back = sides.get(Side.Back);
        Color[][] right = sides.get(Side.Right);

        if (!invert)
        {
            down[0][0] = down[2][0];
            down[2][0] = down[2][2];
            down[2][2] = down[0][2];
            down[0][2] = tempCorner;

            down[0][1] = down[1][0];
            down[1][0] = down[2][1];
            down[2][1] = down[1][2];
            down[0][1] = tempEdge;

            for (int i = 0; i < 3; ++i)
            {
                Color temp = front[2][i];
                front[2][i] = left[2][i];
                left[2][i] = back[2][i];
                back[2][i] = right[2][i];
                right[2][i] = temp;
            }
        }
        else
        {
            down[0][0] = down[0][2];
            down[0][2] = down[2][2];
            down[2][2] = down[2][0];
            down[2][0] = tempCorner;

            down[0][1] = down[1][2];
            down[1][2] = down[2][1];
            down[2][1] = down[1][0];
            down[1][0] = tempEdge;

            for (int i = 0; i < 3; ++i)
            {
                Color temp = front[2][i];
                front[2][i] = right[2][i];
                right[2][i] = back[2][i];
                back[2][i] = left[2][i];
                left[2][i] = temp;
            }
        }
    }

    public void left(boolean invert)
    {
        Log.i("Cube move", "left " + invert);
        Color[][] left = sides.get(Side.Left);
        Color tempCorner = left[0][0];
        Color tempEdge = left[0][1];

        Color[][] up = sides.get(Side.Up);
        Color[][] back = sides.get(Side.Back);
        Color[][] down = sides.get(Side.Down);
        Color[][] front = sides.get(Side.Front);

        if (!invert)
        {
            left[0][0] = left[2][0];
            left[2][0] = left[2][2];
            left[2][2] = left[0][2];
            left[0][2] = tempCorner;

            left[0][1] = left[1][0];
            left[1][0] = left[2][1];
            left[2][1] = left[1][2];
            left[0][1] = tempEdge;

            for (int i = 0; i < 3; ++i)
            {
                Color temp = up[i][0];
                up[i][0] = back[2 - i][2];
                back[2 - i][2] = down[i][0];
                down[i][0] = front[i][0];
                front[i][0] = temp;
            }
        }
        else
        {
            left[0][0] = left[0][2];
            left[0][2] = left[2][2];
            left[2][2] = left[2][0];
            left[2][0] = tempCorner;

            left[0][1] = left[1][2];
            left[1][2] = left[2][1];
            left[2][1] = left[1][0];
            left[1][0] = tempEdge;

            for (int i = 0; i < 3; ++i)
            {
                Color temp = up[i][0];
                up[i][0] = front[i][0];
                front[i][0] = down[i][0];
                down[i][0] = back[2 - i][2];
                back[2 - i][2] = temp;
            }
        }
    }

    public void right(boolean invert)
    {
        Log.i("Cube move", "right " + invert);
        Color[][] right = sides.get(Side.Right);
        Color tempCorner = right[0][0];
        Color tempEdge = right[0][1];

        Color[][] up = sides.get(Side.Up);
        Color[][] front = sides.get(Side.Front);
        Color[][] down = sides.get(Side.Down);
        Color[][] back = sides.get(Side.Back);

        if (!invert)
        {
            right[0][0] = right[2][0];
            right[2][0] = right[2][2];
            right[2][2] = right[0][2];
            right[0][2] = tempCorner;

            right[0][1] = right[1][0];
            right[1][0] = right[2][1];
            right[2][1] = right[1][2];
            right[0][1] = tempEdge;

            for (int i = 0; i < 3; ++i)
            {
                Color temp = up[i][2];
                up[i][2] = front[i][2];
                front[i][2] = down[i][2];
                down[i][2] = back[2 - i][0];
                back[2 - i][0] = temp;
            }
        }
        else
        {
            right[0][0] = right[0][2];
            right[0][2] = right[2][2];
            right[2][2] = right[2][0];
            right[2][0] = tempCorner;

            right[0][1] = right[1][2];
            right[1][2] = right[2][1];
            right[2][1] = right[1][0];
            right[1][0] = tempEdge;

            for (int i = 0; i < 3; ++i)
            {
                Color temp = up[i][2];
                up[i][2] = back[2 - i][0];
                back[2 - i][0] = down[i][2];
                down[i][2] = front[i][2];
                front[i][2] = temp;
            }
        }
    }

    public void front(boolean invert)
    {
        Log.i("Cube move", "front " + invert);
        Color[][] front = sides.get(Side.Front);
        Color tempCorner = front[0][0];
        Color tempEdge = front[0][1];

        Color[][] up = sides.get(Side.Up);
        Color[][] left = sides.get(Side.Left);
        Color[][] down = sides.get(Side.Down);
        Color[][] right = sides.get(Side.Right);

        if (!invert)
        {
            front[0][0] = front[2][0];
            front[2][0] = front[2][2];
            front[2][2] = front[0][2];
            front[0][2] = tempCorner;

            front[0][1] = front[1][0];
            front[1][0] = front[2][1];
            front[2][1] = front[1][2];
            front[0][1] = tempEdge;

            for (int i = 0; i < 3; ++i)
            {
                Color temp = up[2][i];
                up[2][i] = left[2 - i][2];
                left[2 - i][2] = down[0][2 - i];
                down[0][2 - i] = right[i][0];
                right[i][0] = temp;
            }
        }
        else
        {
            front[0][0] = front[0][2];
            front[0][2] = front[2][2];
            front[2][2] = front[2][0];
            front[2][0] = tempCorner;

            front[0][1] = front[1][2];
            front[1][2] = front[2][1];
            front[2][1] = front[1][0];
            front[1][0] = tempEdge;

            for (int i = 0; i < 3; ++i)
            {
                Color temp = up[2][i];
                up[2][i] = left[i][0];
                left[i][0] = down[0][2 - i];
                down[0][2 - i] = left[2 - i][2];
                left[2 - i][2] = temp;
            }
        }
    }

    public void back(boolean invert)
    {
        Log.i("Cube move", "back " + invert);
        Color[][] back = sides.get(Side.Back);
        Color tempCorner = back[0][0];
        Color tempEdge = back[0][1];

        Color[][] up = sides.get(Side.Up);
        Color[][] right = sides.get(Side.Right);
        Color[][] down = sides.get(Side.Down);
        Color[][] left = sides.get(Side.Left);

        if (!invert)
        {
            back[0][0] = back[2][0];
            back[2][0] = back[2][2];
            back[2][2] = back[0][2];
            back[0][2] = tempCorner;

            back[0][1] = back[1][0];
            back[1][0] = back[2][1];
            back[2][1] = back[1][2];
            back[1][2] = tempEdge;

            for (int i = 0; i < 3; ++i)
            {
                Color temp = up[0][i];
                up[0][i] = right[i][2];
                right[i][2] = down[2][2 - i];
                down[2][2 - i] = left[2 - i][0];
                left[2 - i][0] = temp;
            }
        }
        else
        {
            back[0][0] = back[0][2];
            back[0][2] = back[2][2];
            back[2][2] = back[2][0];
            back[2][0] = tempCorner;

            back[0][1] = back[1][2];
            back[1][2] = back[2][1];
            back[2][1] = back[1][0];
            back[1][0] = tempEdge;

            for (int i = 0; i < 3; ++i)
            {
                Color temp = up[0][i];
                up[0][i] = left[2 - i][0];
                left[2 - i][0] = down[2][2 - i];
                down[2][2 - i] = right[i][2];
                right[i][2] = temp;
            }
        }
    }

    public void middle(boolean invert)
    {
        Log.i("Cube move", "middle " + invert);
        Color[][] up = sides.get(Side.Up);
        Color[][] back = sides.get(Side.Back);
        Color[][] down = sides.get(Side.Down);
        Color[][] front = sides.get(Side.Front);

        if (!invert)
        {
            for (int i = 0; i < 3; ++i)
            {
                Color temp = up[i][1];
                up[i][1] = back[2 - i][1];
                back[2 - i][1] = down[i][1];
                down[i][1] = front[i][1];
                front[i][1] = temp;
            }
        }
        else
        {
            for (int i = 0; i < 3; ++i)
            {
                Color temp = up[i][1];
                up[i][1] = front[i][1];
                front[i][1] = down[i][1];
                down[i][1] = back[2 - i][1];
                back[2 - i][1] = temp;
            }
        }
    }

    public void equator(boolean invert)
    {
        Log.i("Cube move", "equator " + invert);
        Color[][] front = sides.get(Side.Front);
        Color[][] left = sides.get(Side.Left);
        Color[][] back = sides.get(Side.Back);
        Color[][] right = sides.get(Side.Right);

        if (!invert)
        {
            for (int i = 0; i < 3; ++i)
            {
                Color temp = front[1][i];
                front[1][i] = left[1][i];
                left[1][i] = back[1][i];
                back[1][i] = right[1][i];
                right[1][i] = temp;
            }
        }
        else
        {
            for (int i = 0; i < 3; ++i)
            {
                Color temp = front[1][i];
                front[1][i] = right[1][i];
                right[1][i] = back[1][i];
                back[1][i] = left[1][i];
                left[1][i] = temp;
            }
        }
    }

    public void standing(boolean invert)
    {
        Log.i("Cube move", "standing " + invert);
        Color[][] up = sides.get(Side.Up);
        Color[][] right = sides.get(Side.Right);
        Color[][] down = sides.get(Side.Down);
        Color[][] left = sides.get(Side.Left);

        if (!invert)
        {
            for (int i = 0; i < 3; ++i)
            {
                Color temp = up[1][i];
                up[1][i] = left[2 - i][1];
                left[2 - i][1] = down[1][2 - i];
                down[1][2 - i] = right[i][1];
                right[i][1] = temp;
            }
        }
        else
        {
            for (int i = 0; i < 3; ++i)
            {
                Color temp = up[1][i];
                up[1][i] = right[i][1];
                right[i][1] = down[1][2 - i];
                down[1][2 - i] = left[2 - i][1];
                left[2 - i][1] = temp;
            }
        }
    }
}
