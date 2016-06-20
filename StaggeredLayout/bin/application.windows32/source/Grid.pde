class grid {
  
  grid() {
    background(255);
    noFill();
    stroke(1);
    // left hand grid outline
    rect(margin, margin, width/4, height - 2*margin);
    //  right side grid outline
    rect(width/4 + 2*margin, margin, 3*width/4 - 3*margin, height - 2*margin);
    // on die number box
    rect(width/4 - margin - 60, 5*step - 4*margin, 40, 39);
  }
  
}