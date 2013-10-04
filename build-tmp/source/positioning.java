import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import toxi.util.datatypes.*; 
import toxi.color.*; 
import geomerative.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class positioning extends PApplet {





/* Constants
--------------------------------------------------------- */

int HORIZONTAL = 0;
int GRID = 1;
int CENTER = 2;
int ROTATION = 3;

int TRIANGLE = 0;
int ELLIPSE = 1;
int RECTANGLE = 2;

/* Variables
--------------------------------------------------------- */

int shapeType;
int shapeSize;
int shapeSpacing;
int shapeRotation;
int numShapes;
int positionType;

// TODO
// int shapeSpacingType = EASING, EQUAL, NONE
// int shapeDisplacement = RANDOM, X, Y, 

RShape theShape;

/* Test Stuff
--------------------------------------------------------- */

ColorList colors = new ColorList();

/* Setup
--------------------------------------------------------- */

public void setup()
{
  size(1200, 800);
  colorMode(HSB, 1, 1, 1, 1);
  smooth();
  noStroke();
  RG.init(this);

  // test colors
  colors.add( TColor.newHSV(0, 1, 0.3f) );
  colors.add( TColor.newHSV(0, 0.92f, 0.51f) );
  colors.add( TColor.newHSV(0, 0.96f, 0.85f) );
  colors.add( TColor.newHSV(0.24f, 0.84f, 0.2f) );
  colors.add( TColor.newHSV(0.24f, 0.79f, 0.38f) );
  colors.add( TColor.newHSV(0.27f, 0.61f, 0.64f) );
  colors.add( TColor.newHSV(0.57f, 0.28f, 0.48f) );
  colors.add( TColor.newHSV(0.57f, 0.18f, 0.59f) );
  colors.add( TColor.newHSV(0.56f, 0.13f, 0.82f) );
  colors.add( TColor.newHSV(0.12f, 0.94f, 0.59f) );
  colors.add( TColor.newHSV(0.12f, 0.91f, 0.89f) );
  colors.add( TColor.newHSV(0.16f, 0.83f, 0.99f) );

  createNew();
}

/* Draw
--------------------------------------------------------- */

public void draw()
{
  background(1);
  translate((width/2) - (theShape.getWidth()/2), (height/2) - (theShape.getHeight()/2));
  theShape.draw();
}

/* Create New
--------------------------------------------------------- */

public void createNew()
{
  theShape = new RShape();
  
  chooseNumShapes();
  chooseShapeSize();
  chooseShapeType();
  chooseShapeSpacing();
  chooseShapeRotation();
  choosePosition();

  generateShapes();
}

/* Choose: Num Shapes
--------------------------------------------------------- */

public void chooseNumShapes()
{
  WeightedRandomSet<Integer> nums = new WeightedRandomSet<Integer>();
  nums.add(1, 1);
  nums.add(round(random(2, 5)), 1);
  nums.add(round(random(6, 20)), 1);
  numShapes = nums.getRandom();
}

/* Choose: Shape Size
--------------------------------------------------------- */

public void chooseShapeSize()
{
  shapeSize = round(random(40, 500));
}

/* Choose: Shape Type
--------------------------------------------------------- */

public void chooseShapeType()
{
  WeightedRandomSet<Integer> shapes = new WeightedRandomSet<Integer>();
  shapes.add(TRIANGLE, 1);
  shapes.add(RECTANGLE, 1);
  shapes.add(ELLIPSE, 1);

  // we could manipulate it here in size
  // we could add numerous shapes?

  shapeType = shapes.getRandom();
}

/* Choose: Shape Spacing
--------------------------------------------------------- */

public void chooseShapeSpacing()
{
  WeightedRandomSet<Integer> spacings = new WeightedRandomSet<Integer>();
  spacings.add(0, 1);
  spacings.add(round(shapeSize/2), 1);
  spacings.add(shapeSize, 1);
  spacings.add(round(random(500)), 1);
  shapeSpacing = spacings.getRandom();
}

/* Choose: Shape Rotation
--------------------------------------------------------- */

public void chooseShapeRotation()
{
  WeightedRandomSet<Integer> rotations = new WeightedRandomSet<Integer>();
  rotations.add(0, 8);
  rotations.add(45, 1);
  rotations.add(90, 1);
  rotations.add(135, 1);
  rotations.add(180, 4);
  rotations.add(225, 1);
  rotations.add(270, 1);
  rotations.add(315, 1);
  rotations.add(round(random(360)), 2);
  shapeRotation = rotations.getRandom();
}


/* Choose: Position
--------------------------------------------------------- */

public void choosePosition()
{
  WeightedRandomSet<Integer> positions = new WeightedRandomSet<Integer>();
  positions.add(HORIZONTAL, 1);
  positions.add(GRID, 1);
  positions.add(CENTER, 1);
  positions.add(ROTATION, 1);
  positionType = positions.getRandom();
}

public void generateShapes()
{
  RShape newShape;

  positionType = GRID;
  shapeType = TRIANGLE;

  // horizontal
  if(positionType == HORIZONTAL)
  {
    for(int i = 0; i < numShapes; i++)
    {
      int x = (i * shapeSize) + (i * shapeSpacing);
      newShape = getShapeType(shapeType);
      newShape.translate(x, 0);
      newShape.rotate(radians(shapeRotation * i), new RPoint(newShape.getX() + (shapeSize/2), shapeSize/2));
      theShape.addChild(newShape);
    }
  }

  // grid
  else if(positionType == GRID)
  {
    for(int i = 0; i < numShapes; i++)
    {
      for(int j = 0; j < numShapes; j++)
      {
        int x = (i * shapeSize) + (i * shapeSpacing);
        int y = (j * shapeSize) + (j * shapeSpacing);
        newShape = getShapeType(shapeType);
        newShape.translate(x, y);
        newShape.rotate(radians(shapeRotation * i), new RPoint(newShape.getX() + (shapeSize/2), newShape.getY() + (shapeSize/2)));
        theShape.addChild(newShape);
      }
    }
  }

  // center
  else if(positionType == CENTER)
  {

  }

  // rotation
  else if(positionType == ROTATION)
  {

  }

  // set colors
  for(int i = 0; i < theShape.children.length; i++)
  {
    TColor col = colors.get(i % colors.size());
    theShape.children[i].setFill(col.toARGB());
  }
}

public RShape getShapeType(int type)
{
  RShape returnShape;

  if(type == ELLIPSE)
  {
    returnShape = RShape.createCircle(0, 0, shapeSize);
    returnShape.translate(shapeSize/2, shapeSize/2);
  }
  else if(type == RECTANGLE)
  {
    returnShape = RShape.createRectangle(0, 0, shapeSize, shapeSize);
  }
  else
  {
    float half = shapeSize/2;
    returnShape = new RShape();
    returnShape.addMoveTo(0, -half);
    returnShape.addLineTo(half, half);
    returnShape.addLineTo(-half, half);
    returnShape.addLineTo(0, -half);
  }

  return returnShape;
}

/* Events
--------------------------------------------------------- */

public void keyPressed()
{
  if(key == 'r')
  {
    createNew();
  }
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "positioning" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
