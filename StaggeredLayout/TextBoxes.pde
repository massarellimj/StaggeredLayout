class textBoxes {
  
  Textfield matWidth, pDiameter, spc, adv;
  int textBoxWidth, textBoxHeight;
  float originX, originY;
  
  textBoxes(ControlP5 cp5) {
    textBoxWidth = 100;
    textBoxHeight = 30;
    originX = width / 4 - 100 - margin;
    originY = margin + (step/2) - (textBoxHeight/2);
    
    matWidth = cp5.addTextfield("materialWidth")
              .setPosition(originX, originY)
              .setSize(textBoxWidth, textBoxHeight)
              .setFocus(true)
              .setColorBackground(color(255,255,255))
              .setColorForeground(255)
              .setColorValue(0)
              .setFont(font)
              .setAutoClear(false)
              .setInputFilter(2);
                
    pDiameter = cp5.addTextfield("partDiameter")
                   .setPosition(originX, originY + step)
                   .setSize(textBoxWidth, textBoxHeight)
                   .setColorBackground(color(255,255,255))
                   .setColorForeground(255)
                   .setColorValue(0)
                   .setFont(font)
                   .setAutoClear(false)
                   .setInputFilter(2);
                   
    spc = cp5.addTextfield("spacing")
             .setPosition(originX, originY + 2*step)
             .setSize(textBoxWidth, textBoxHeight)
             .setColorBackground(color(255,255,255))
             .setColorForeground(255)
             .setColorValue(0)
             .setFont(font)
             .setAutoClear(false)
             .setInputFilter(2);
             
    adv = cp5.addTextfield("advance")
             .setPosition(originX, originY + 3*step)
             .setSize(textBoxWidth, textBoxHeight)
             .setColorBackground(color(255,255,255))
             .setColorForeground(255)
             .setColorValue(0)
             .setFont(font)
             .setAutoClear(false)
             .setInputFilter(2);
  }

}