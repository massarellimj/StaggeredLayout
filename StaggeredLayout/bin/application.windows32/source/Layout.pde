// 864 x 676 pixels to work with
// from vertex(324, 12) to vertex(1188, 688) plus an additional 12 pixel outer margin

class stagger {

  float diameter, advance, spacing, around, across, onDie, dieWidth, dieDepth, 
    newDiameter, newAdvance, newSpacing, newAround, newAcross, newWidth, 
    newDepth, centerX, centerY, firstLeft, firstRight, leftX, rightX, topY, 
    bottomY, row1, row2, matWidth, slits, slitWidth;

  PFont font3;

  stagger(float D, float S, float O, float M) {
    diameter = D;
    spacing = S;
    advance = diameter + spacing;
    onDie = O;
    around = advance / 2;
    across = sqrt(sq(advance) - sq(around));
    dieWidth = diameter + across*(onDie - 1);
    dieDepth = advance;
    leftX = 380;
    rightX = 1100;
    topY = 12;
    bottomY = 688;
    newDiameter = diameter * (rightX - leftX) / dieWidth;
    newSpacing = spacing * newDiameter / diameter;
    newAdvance = newDiameter + newSpacing;
    newAround = newAdvance / 2;
    newAcross = sqrt(sq(newAdvance) - sq(newAround));
    newWidth = newDiameter + newAcross*(onDie - 1);
    centerX = (rightX - leftX) / 2 + leftX;
    centerY = (bottomY- topY) / 2 + topY;
    row1 = centerY + (newAround / 2);
    row2 = row1 - newAround;
    firstLeft = centerX - (newWidth / 2) + (newDiameter / 2);
    firstRight = firstLeft + (newAcross * (onDie - 1));
    matWidth = M;
    slits = floor(matWidth / (dieWidth + 0.5));
    slitWidth = matWidth / slits;
    font3 = createFont("arial", 14);
  }

  // this function will create the circle parts excluding the advanced parts in grey
  void parts() {
    noFill();
    stroke(0);
    for (int i = 0; i < onDie; i++) {
      // if the number of cavities is even...
      if (i % 2 == 0) {
        stroke(0);
        ellipse(firstLeft + newAcross * i, row1, newDiameter, newDiameter);
        // if the number of cavities is odd...
      } else {
        stroke(0);
        ellipse(firstLeft + newAcross * i, row2, newDiameter, newDiameter);
      }
    }
  }

// this function will only create the advanced grey circle parts
  void advParts() {
    noFill();
    stroke(160);
    int inc = int(radians(10));
    for (int i = 0; i < onDie; i++) {
      // if the number of cavities is even...
      if (i % 2 == 0) {
        noFill();
        stroke(160);
        ellipse(firstLeft + newAcross * i, row1 - newAdvance, newDiameter, newDiameter);
        //for (int a = 0; a < TWO_PI; a=+inc) {
        //  noStroke();
        //  fill(255);
        //  triangle(firstLeft + newAcross * i, row1 - newAdvance, firstLeft + newAcross * i + cos(a)*(newDiameter+5), row1 - newAdvance + sin(a)*(newDiameter+5), firstLeft + newAcross * i + cos(a+radians(5))*(newDiameter+5), row1 - newAdvance + sin(a+radians(5))*(newDiameter+5));
        //}
        
        // if the number of cavities is odd...
      } else {
        noFill();
        stroke(160);
        ellipse(firstLeft + newAcross * i, row2 - newAdvance, newDiameter, newDiameter);
      }
    }
  }
  
  // this function will create all of the dimensions including lines, text, and values
  void dimensions() {
    float d = newDiameter/8;
    fill(0);
    stroke(0);
    textFont(font);

    // part diameter dimension
    float xO = firstLeft + newAcross + cos(radians(60))*(newDiameter / 2);
    float yO = row1 - newAdvance - newAround + sin(radians(60))*(newDiameter / 2);
    line(xO, yO, firstLeft + newAcross - cos(radians(60))*(newDiameter / 2), row1 - newAdvance - newAround - sin(radians(60))*(newDiameter / 2));
    line(firstLeft + newAcross - cos(radians(60))*(newDiameter / 2), row1 - newAdvance - newAround - sin(radians(60))*(newDiameter / 2), firstLeft, row1 - newAdvance - newAround - sin(radians(60))*(newDiameter / 2));
    triangle(xO, yO, xO - cos(radians(45.7)) * d, yO - sin(radians(45.7)) * d, xO - cos(radians(74.3))* d, yO - sin(radians(74.3)) * d);
    xO = firstLeft + newAcross - cos(radians(60))*(newDiameter / 2);
    yO = row1 - newAdvance - newAround - sin(radians(60))*(newDiameter / 2);
    triangle(xO, yO, xO + cos(radians(45.7)) * d, yO + sin(radians(45.7)) * d, xO + cos(radians(74.3))* d, yO + sin(radians(74.3)) * d);
    textAlign(LEFT);
    text(str(diameter) + "in. O.D.", firstLeft, row1 - newAdvance - newAround - sin(radians(60))*(newDiameter / 2) - newSpacing / 4);

    // spacing dimension
    line(firstLeft + (newAcross / 2), row1 - (newAround / 2), firstLeft + (newDiameter / 4) + (newSpacing / 2), row1 - (newDiameter / 2) - (newSpacing / 2));
    line(firstLeft + (newDiameter / 4) + (newSpacing / 2), row1 - (newDiameter / 2) - (newSpacing / 2), firstLeft - (newDiameter / 2), row1 - (newDiameter / 2) - (newSpacing / 2));
    triangle(firstLeft + (newAcross / 2), row1 - (newAround / 2), firstLeft + (newAcross / 2) - cos(radians(45.7))*d, row1 - (newAround / 2) - sin(radians(45.7))*d, firstLeft + (newAcross / 2) - cos(radians(74.3))*d, row1 - (newAround / 2) - sin(radians(74.3))*d);
    textAlign(RIGHT);
    text(str(spacing) + "in. Spacing", firstLeft + (newDiameter / 4) + (newSpacing / 2), row1 - (newDiameter / 2) - newSpacing);

    // die width dimension
    line(firstLeft - (newDiameter / 2), row1 + newSpacing, firstLeft - (newDiameter / 2), row1 + (newDiameter));
    if (onDie % 2 == 0) {
      line(firstLeft + (newAcross * (onDie - 1)) + (newDiameter / 2), row2 + newSpacing, firstLeft + (newAcross * (onDie - 1)) + (newDiameter / 2), row1 + newDiameter);
    } else {
      line(firstLeft + (newAcross * (onDie - 1)) + (newDiameter / 2), row1 + newSpacing, firstLeft + (newAcross * (onDie - 1)) + (newDiameter / 2), row1 + newDiameter);
    }
    line(firstLeft - (newDiameter / 2), row1 + newDiameter - newSpacing, firstRight + (newDiameter / 2), row1 + newDiameter - newSpacing);
    triangle(firstLeft - (newDiameter / 2) + 2, row1 + newDiameter - newSpacing, firstLeft - (newDiameter / 2) + newSpacing, row1 + newDiameter - (5*newSpacing / 4), firstLeft - (newDiameter / 2) + newSpacing, row1 + newDiameter - (3*newSpacing / 4));
    triangle(firstRight + (newDiameter / 2) - 2, row1 + newDiameter - newSpacing, firstRight + (newDiameter / 2) - newSpacing, row1 + newDiameter - (5*newSpacing / 4), firstRight + (newDiameter / 2) - newSpacing, row1 + newDiameter - (3*newSpacing / 4));
    textAlign(CENTER);
    text("Die Width:  " + str(dieWidth), centerX, row1 + newDiameter);

    // advance dimension
    if (onDie % 2 == 0) {
      stroke(0);
      line(firstRight, row2 + (newDiameter / 2), rightX + (newDiameter / 2), row2 + (newDiameter / 2));
      line(firstRight, row2 + (newDiameter / 2) - newAdvance, rightX + (newDiameter / 2), row2 + (newDiameter / 2) - newAdvance);
      line(rightX + (newDiameter / 2) - newSpacing, row2 + (newDiameter / 2), rightX + (newDiameter / 2) - newSpacing, row2 + newSpacing);
      line(rightX + (newDiameter / 2) - newSpacing, row2 + (newDiameter / 2) - newAdvance, rightX + (newDiameter / 2) - newSpacing, row2 - (3*newSpacing/2));
      triangle(rightX + (newDiameter / 2) - newSpacing, row2 + (newDiameter / 2), rightX + (newDiameter / 2) - (5* newSpacing  / 4), row2 + (newDiameter / 2) - newSpacing, rightX + (newDiameter / 2) - (3*newSpacing / 4), row2 + (newDiameter / 2) - newSpacing);
      triangle(rightX + (newDiameter / 2) - newSpacing, row2 + (newDiameter / 2) - newAdvance, rightX + (newDiameter / 2) - (5* newSpacing  / 4), row2 + (newDiameter / 2) + newSpacing - newAdvance, rightX + (newDiameter / 2) - (3*newSpacing / 4), row2 + (newDiameter / 2) + newSpacing - newAdvance);
      textAlign(CENTER);
      text("Advance", rightX + (newDiameter / 2) - newSpacing, row2 + (newSpacing / 2));
      text(str(advance)+"in", rightX + (newDiameter / 2) - newSpacing, row2 - (newSpacing / 2));
    } else {
      stroke(0);
      line(firstRight, row1 + (newDiameter / 2), rightX + (newDiameter / 2), row1 + (newDiameter / 2));
      line(firstRight, row1 + (newDiameter / 2) - newAdvance, rightX + (newDiameter / 2), row1 + (newDiameter / 2) - newAdvance);
      line(rightX + (newDiameter / 2) - newSpacing, row1 + (newDiameter / 2), rightX + (newDiameter / 2) - newSpacing, row1 + newSpacing);
      line(rightX + (newDiameter / 2) - newSpacing, row1 + (newDiameter / 2) - newAdvance, rightX + (newDiameter / 2) - newSpacing, row1 - (3*newSpacing/2));
      triangle(rightX + (newDiameter / 2) - newSpacing, row1 + (newDiameter / 2), rightX + (newDiameter / 2) - (5* newSpacing  / 4), row1 + (newDiameter / 2) - newSpacing, rightX + (newDiameter / 2) - (3*newSpacing / 4), row1 + (newDiameter / 2) - newSpacing);
      triangle(rightX + (newDiameter / 2) - newSpacing, row1 + (newDiameter / 2) - newAdvance, rightX + (newDiameter / 2) - (5* newSpacing  / 4), row1 + (newDiameter / 2) + newSpacing - newAdvance, rightX + (newDiameter / 2) - (3*newSpacing / 4), row1 + (newDiameter / 2) + newSpacing - newAdvance);
      textAlign(CENTER);
      text("Advance", rightX + (newDiameter / 2) - newSpacing, row1 + (newSpacing / 2));
      text(str(advance)+"in", rightX + (newDiameter / 2) - newSpacing, row1 - (newSpacing / 2));
    }

    // material width dimensions
    line(firstLeft - (5 * newDiameter / 8), row1 - (newDiameter / 4), firstLeft - (5 * newDiameter / 8), row1 + (5 * newDiameter / 4));
    line(firstRight + (5 * newDiameter / 8), row1 - (newDiameter / 4), firstRight + (5 * newDiameter / 8), row1 + (5 * newDiameter / 4));
    line(firstLeft - (5 * newDiameter / 8), row1 + (7 * newDiameter / 6), firstRight + (5 * newDiameter / 8), row1 + (7 * newDiameter / 6));
    triangle(firstLeft - (5 * newDiameter / 8) + 2, row1 + (7 * newDiameter / 6), firstLeft - (newDiameter/2), row1 + (7 * newDiameter / 6) + (newDiameter / 32), firstLeft - (newDiameter / 2), row1 + (7 * newDiameter / 6) - (newDiameter / 32));
    triangle(firstRight + (5 * newDiameter / 8) - 2, row1 + (7 * newDiameter / 6), firstRight + (newDiameter/2), row1 + (7 * newDiameter / 6) + (newDiameter / 32), firstRight + (newDiameter / 2), row1 + (7 * newDiameter / 6) - (newDiameter / 32));
    textAlign(CENTER);
    text("Material Width:  " + str(slitWidth), centerX, row1 + (4 * newDiameter / 3));
  }

  void layout() {
    dimensions();
    parts();
    advParts();
  }
}