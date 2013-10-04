import toxi.util.datatypes.*;
import toxi.color.*;
import geomerative.*;

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

void setup()
{
  size(1200, 800);
  colorMode(HSB, 1, 1, 1, 1);
  smooth();
  noStroke();
  RG.init(this);

  // test colors
  colors.add( TColor.newHSV(0, 1, 0.3) );
  colors.add( TColor.newHSV(0, 0.92, 0.51) );
  colors.add( TColor.newHSV(0, 0.96, 0.85) );
  colors.add( TColor.newHSV(0.24, 0.84, 0.2) );
  colors.add( TColor.newHSV(0.24, 0.79, 0.38) );
  colors.add( TColor.newHSV(0.27, 0.61, 0.64) );
  colors.add( TColor.newHSV(0.57, 0.28, 0.48) );
  colors.add( TColor.newHSV(0.57, 0.18, 0.59) );
  colors.add( TColor.newHSV(0.56, 0.13, 0.82) );
  colors.add( TColor.newHSV(0.12, 0.94, 0.59) );
  colors.add( TColor.newHSV(0.12, 0.91, 0.89) );
  colors.add( TColor.newHSV(0.16, 0.83, 0.99) );

  createNew();
}

/* Draw
--------------------------------------------------------- */

void draw()
{
  background(1);
  translate((width/2) - (theShape.getWidth()/2), (height/2) - (theShape.getHeight()/2));
  theShape.draw();
}

/* Create New
--------------------------------------------------------- */

void createNew()
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

void chooseNumShapes()
{
  WeightedRandomSet<Integer> nums = new WeightedRandomSet<Integer>();
  nums.add(1, 1);
  nums.add(round(random(2, 5)), 1);
  nums.add(round(random(6, 20)), 1);
  numShapes = nums.getRandom();
}

/* Choose: Shape Size
--------------------------------------------------------- */

void chooseShapeSize()
{
  shapeSize = round(random(40, 500));
}

/* Choose: Shape Type
--------------------------------------------------------- */

void chooseShapeType()
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

void chooseShapeSpacing()
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

void chooseShapeRotation()
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
  shapeRotation = rotations.getRandom();
}


/* Choose: Position
--------------------------------------------------------- */

void choosePosition()
{
  WeightedRandomSet<Integer> positions = new WeightedRandomSet<Integer>();
  positions.add(HORIZONTAL, 1);
  positions.add(GRID, 1);
  positions.add(CENTER, 1);
  positions.add(ROTATION, 1);
  positionType = positions.getRandom();
}

void generateShapes()
{
  RShape newShape;

  positionType = GRID;

  // horizontal
  if(positionType == HORIZONTAL)
  {
    for(int i = 0; i < numShapes; i++)
    {
      int x = (i * shapeSize) + (i * shapeSpacing);
      newShape = getShapeType(shapeType);
      newShape.translate(x, 0);
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

RShape getShapeType(int type)
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
  else { // THIS SHOULD BE A TRIANGLE
    returnShape = RShape.createCircle(0, 0, shapeSize);
  }

  return returnShape;
}

/* Events
--------------------------------------------------------- */

void keyPressed()
{
  if(key == 'r')
  {
    createNew();
  }
}