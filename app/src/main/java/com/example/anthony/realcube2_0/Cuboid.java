package com.example.anthony.realcube2_0;

import java.util.ArrayList;

public class Cuboid extends TwistyPuzzle
{
    private int width;
    private int height;
    private int depth;
    private float sideLength;   //the length of the side of one tile (since not all
                                //sides of a cuboid are necessarily the same length
    private float[][] colors;
    private float spacing;

    public Cuboid(int w, int h, int d, float sl, float sp, float[][] mColors)
    {
        width = w;
        height = h;
        depth = d;
        sideLength = sl;
        spacing = sp;
        colors = mColors;
        init();
    }

    public Cuboid()
    {
        width = 3;
        height = 3;
        depth = 3;
        sideLength = 0.5f;
        spacing = 0.1f;
        float alpha = 1f;

        float[] white = {0xff / 256f, 0xff / 256f, 0xff / 256f, alpha};    //up
        float[] yellow = {0xff / 256f, 0xd5 / 256f, 0x00 / 256f, alpha};   //down
        float[] red = {0xc4 / 256f, 0x1e / 256f, 0x3a / 256f, alpha};      //left
        float[] orange = {0xff / 256f, 0x58 / 256f, 0x00 / 256f, alpha};   //right
        float[] blue = {0x00 / 256f, 0x51 / 256f, 0xba / 256f, alpha};     //front
        float[] green = {0x00 / 256f, 0x9e / 256f, 0x60 / 256f, alpha};    //back

        colors = new float[][] {white, yellow, red, orange, blue, green};
        init();
    }

    private void init()
    {
        pieces = new ArrayList<Piece>();

        //generate pieces of cuboid and add them to @pieces

        float unit = sideLength + spacing;
        float halfUnit = unit / 2f;
        float widthRadius = width * halfUnit;
        float heightRadius = height * halfUnit;
        float depthRadius = depth * halfUnit;

        float[] up = Square.generateFace(width, depth, sideLength, spacing, Cube3x3x3.Side.Up, heightRadius);
        float[] down = Square.generateFace(width, depth, sideLength, spacing, Cube3x3x3.Side.Down, heightRadius);
        float[] left = Square.generateFace(depth, height, sideLength, spacing, Cube3x3x3.Side.Left, widthRadius);
        float[] right = Square.generateFace(depth, height, sideLength, spacing, Cube3x3x3.Side.Right, widthRadius);
        float[] front = Square.generateFace(width, height, sideLength, spacing, Cube3x3x3.Side.Front, depthRadius);
        float[] back = Square.generateFace(width, height, sideLength, spacing, Cube3x3x3.Side.Back, depthRadius);

        int squareStride = Square.verticesPerSquare * Square.coordsPerVertex;
        int centerStride = squareStride;
        int edgeStride = 2 * squareStride;
        int cornerStride = 3 * squareStride;

        int colorStride = 4;
        int centerColorLength = colorStride;
        int edgeColorLength = 2 * colorStride;
        int cornerColorLength = 3 * colorStride;


        //the 8 corners. If any of the dimensions is 1, some of the corners will overlap for now; will optimize later
        float[] ulf = new float[cornerStride];      float[][] ulfColors = new float[3][colorStride];
        float[] ulb = new float[cornerStride];      float[][] ulbColors = new float[3][colorStride];
        float[] urf = new float[cornerStride];      float[][] urfColors = new float[3][colorStride];
        float[] urb = new float[cornerStride];      float[][] urbColors = new float[3][colorStride];
        float[] dlf = new float[cornerStride];      float[][] dlfColors = new float[3][colorStride];
        float[] dlb = new float[cornerStride];      float[][] dlbColors = new float[3][colorStride];
        float[] drf = new float[cornerStride];      float[][] drfColors = new float[3][colorStride];
        float[] drb = new float[cornerStride];      float[][] drbColors = new float[3][colorStride];

        System.arraycopy(up, (depth - 1) * squareStride, ulf, 0, squareStride);
        System.arraycopy(left, (depth - 1) * height * squareStride, ulf, squareStride, squareStride);
        System.arraycopy(front, 0, ulf, 2 * squareStride, squareStride);

        System.arraycopy(up, 0, ulb, 0, squareStride);
        System.arraycopy(left, 0, ulb, squareStride, squareStride);
        System.arraycopy(back, (width - 1) * height * squareStride, ulb, 2 * squareStride, squareStride);

        System.arraycopy(up, up.length - squareStride, urf, 0, squareStride);
        System.arraycopy(right, 0, urf, squareStride, squareStride);
        System.arraycopy(front, (width - 1) * height * squareStride, urf, 2 * squareStride, squareStride);

        System.arraycopy(up, (width - 1) * depth * squareStride, urb, 0, squareStride);
        System.arraycopy(right, (depth - 1) * height * squareStride, urb, squareStride, squareStride);
        System.arraycopy(back, 0, urb, 2 * squareStride, squareStride);

        System.arraycopy(down, 0, dlf, 0, squareStride);
        System.arraycopy(left, left.length - squareStride, dlf, squareStride, squareStride);
        System.arraycopy(front, (height - 1) * squareStride, dlf, 2 * squareStride, squareStride);

        System.arraycopy(down, (depth - 1) * squareStride, dlb, 0, squareStride);
        System.arraycopy(left, (height - 1) * squareStride, dlb, squareStride, squareStride);
        System.arraycopy(back, back.length - squareStride, dlb, 2 * squareStride, squareStride);

        System.arraycopy(down, (width - 1) * depth * squareStride, drf, 0, squareStride);
        System.arraycopy(right, (height - 1) * squareStride, drf, squareStride, squareStride);
        System.arraycopy(front, front.length - squareStride, drf, 2 * squareStride, squareStride);

        System.arraycopy(down, down.length - squareStride, drb, 0, squareStride);
        System.arraycopy(right, right.length - squareStride, drb, squareStride, squareStride);
        System.arraycopy(back, (height - 1) * squareStride, drb, 2 * squareStride, squareStride);

        ulfColors[0] = colors[0];
        ulfColors[1] = colors[2];
        ulfColors[2] = colors[4];

        ulbColors[0] = colors[0];
        ulbColors[1] = colors[2];
        ulbColors[2] = colors[5];

        urfColors[0] = colors[0];
        urfColors[1] = colors[3];
        urfColors[2] = colors[4];

        urbColors[0] = colors[0];
        urbColors[1] = colors[3];
        urbColors[2] = colors[5];


        dlfColors[0] = colors[1];
        dlfColors[1] = colors[2];
        dlfColors[2] = colors[4];

        dlbColors[0] = colors[1];
        dlbColors[1] = colors[2];
        dlbColors[2] = colors[5];

        drfColors[0] = colors[1];
        drfColors[1] = colors[3];
        drfColors[2] = colors[4];

        drbColors[0] = colors[1];
        drbColors[1] = colors[3];
        drbColors[2] = colors[5];


        Piece ulfPiece = new PieceCuboidCorner(ulf, ulfColors);
        Piece ulbPiece = new PieceCuboidCorner(ulb, ulbColors);
        Piece urfPiece = new PieceCuboidCorner(urf, urfColors);
        Piece urbPiece = new PieceCuboidCorner(urb, urbColors);
        Piece dlfPiece = new PieceCuboidCorner(dlf, dlfColors);
        Piece dlbPiece = new PieceCuboidCorner(dlb, dlbColors);
        Piece drfPiece = new PieceCuboidCorner(drf, drfColors);
        Piece drbPiece = new PieceCuboidCorner(drb, drbColors);

        pieces.add(ulfPiece);
        pieces.add(ulbPiece);
        pieces.add(urfPiece);
        pieces.add(urbPiece);
        pieces.add(dlfPiece);
        pieces.add(dlbPiece);
        pieces.add(drfPiece);
        pieces.add(drbPiece);


        //create edge pieces
        if (width > 2)
        {
            float[][] uf = new float[width - 2][edgeStride];    float[][] ufColors = new float[2][colorStride];
            float[][] ub = new float[width - 2][edgeStride];    float[][] ubColors = new float[2][colorStride];
            float[][] df = new float[width - 2][edgeStride];    float[][] dfColors = new float[2][colorStride];
            float[][] db = new float[width - 2][edgeStride];    float[][] dbColors = new float[2][colorStride];

            ufColors[0] = colors[0];
            ufColors[1] = colors[4];
            ubColors[0] = colors[0];
            ubColors[1] = colors[5];
            dfColors[0] = colors[1];
            dfColors[1] = colors[4];
            dbColors[0] = colors[1];
            dbColors[1] = colors[5];

            for (int x = 1; x < width - 1; ++x)
            {
                System.arraycopy(up, ((1 + x) * depth - 1) * squareStride, uf[x - 1], 0, squareStride);
                System.arraycopy(front, height * x * squareStride, uf[x - 1], squareStride, squareStride);

                System.arraycopy(up, depth * x * squareStride, ub[x - 1], 0, squareStride);
                System.arraycopy(back, back.length - (1 + x) * height * squareStride, ub[x - 1], squareStride, squareStride);

                System.arraycopy(down, x * depth * squareStride, df[x - 1], 0, squareStride);
                System.arraycopy(front, ((1 + x) * height - 1) * squareStride, df[x - 1], squareStride, squareStride);

                System.arraycopy(down, ((1 + x) * depth - 1) * squareStride, db[x - 1], 0, squareStride);
                System.arraycopy(back, back.length - squareStride - x * height * squareStride, db[x - 1], squareStride, squareStride);

                Piece ufEdge = new PieceCuboidEdge(uf[x - 1], ufColors);
                Piece ubEdge = new PieceCuboidEdge(ub[x - 1], ubColors);
                Piece dfEdge = new PieceCuboidEdge(df[x - 1], dfColors);
                Piece dbEdge = new PieceCuboidEdge(db[x - 1], dbColors);

                pieces.add(ufEdge);
                pieces.add(ubEdge);
                pieces.add(dfEdge);
                pieces.add(dbEdge);
            }
        }

        if (height > 2)
        {
            float[][] lf = new float[height - 2][edgeStride];   float[][] lfColors = new float[2][colorStride];
            float[][] lb = new float[height - 2][edgeStride];   float[][] lbColors = new float[2][colorStride];
            float[][] rf = new float[height - 2][edgeStride];   float[][] rfColors = new float[2][colorStride];
            float[][] rb = new float[height - 2][edgeStride];   float[][] rbColors = new float[2][colorStride];

            lfColors[0] = colors[2];
            lfColors[1] = colors[4];
            lbColors[0] = colors[2];
            lbColors[1] = colors[5];
            rfColors[0] = colors[3];
            rfColors[1] = colors[4];
            rbColors[0] = colors[3];
            rbColors[1] = colors[5];

            for (int y = 1; y < height - 1; ++y)
            {
                System.arraycopy(left, (depth - 1) * height * squareStride + y * squareStride, lf[y - 1], 0, squareStride);
                System.arraycopy(front, y * squareStride, lf[y - 1], squareStride, squareStride);

                System.arraycopy(left, y * squareStride, lb[y - 1],  0, squareStride);
                System.arraycopy(back, (width - 1) * height * squareStride + y * squareStride, lb[y - 1], squareStride, squareStride);

                System.arraycopy(right, y * squareStride, rf[y - 1], 0, squareStride);
                System.arraycopy(front, (width - 1) * height * squareStride + y * squareStride, rf[y - 1], squareStride, squareStride);

                System.arraycopy(right, (depth - 1) * height * squareStride + y * squareStride, rb[y - 1], 0, squareStride);
                System.arraycopy(back, y * squareStride, rb[y - 1], squareStride, squareStride);

                Piece lfEdge = new PieceCuboidEdge(lf[y - 1], lfColors);
                Piece lbEdge = new PieceCuboidEdge(lb[y - 1], lbColors);
                Piece rfEdge = new PieceCuboidEdge(rf[y - 1], rfColors);
                Piece rbEdge = new PieceCuboidEdge(rb[y - 1], rbColors);

                pieces.add(lfEdge);
                pieces.add(lbEdge);
                pieces.add(rfEdge);
                pieces.add(rbEdge);
            }


        }

        if (depth > 2)
        {
            float[][] ul = new float[depth - 2][edgeStride];    float[][] ulColors = new float[2][colorStride];
            float[][] ur = new float[depth - 2][edgeStride];    float[][] urColors = new float[2][colorStride];
            float[][] dl = new float[depth - 2][edgeStride];    float[][] dlColors = new float[2][colorStride];
            float[][] dr = new float[depth - 2][edgeStride];    float[][] drColors = new float[2][colorStride];

            ulColors[0] = colors[0];
            ulColors[1] = colors[2];
            urColors[0] = colors[0];
            urColors[1] = colors[3];
            dlColors[0] = colors[1];
            dlColors[1] = colors[2];
            drColors[0] = colors[1];
            drColors[1] = colors[3];

            for (int z = 1; z < depth - 1; ++z)
            {
                System.arraycopy(up, z * squareStride, ul[z - 1], 0, squareStride);
                System.arraycopy(left, z * height * squareStride, ul[z - 1], squareStride, squareStride);

                System.arraycopy(up, (width - 1) * depth * squareStride + z * squareStride, ur[z - 1], 0, squareStride);
                System.arraycopy(right, (depth - 1) * height * squareStride - z * height * squareStride, ur[z - 1], squareStride, squareStride);

                System.arraycopy(down, z * squareStride, dl[z - 1], 0, squareStride);
                System.arraycopy(left, left.length - squareStride - z * height * squareStride, dl[z - 1], squareStride, squareStride);

                System.arraycopy(down, (width - 1) * depth * squareStride + z * squareStride, dr[z - 1], 0, squareStride);
                System.arraycopy(right, (height - 1) * squareStride + z * height * squareStride, dr[z - 1], squareStride, squareStride);

                Piece ulEdge = new PieceCuboidEdge(ul[z - 1], ulColors);
                Piece urEdge = new PieceCuboidEdge(ur[z - 1], urColors);
                Piece dlEdge = new PieceCuboidEdge(dl[z - 1], dlColors);
                Piece drEdge = new PieceCuboidEdge(dr[z - 1], drColors);

                pieces.add(ulEdge);
                pieces.add(urEdge);
                pieces.add(dlEdge);
                pieces.add(drEdge);
            }
        }
    }

}
