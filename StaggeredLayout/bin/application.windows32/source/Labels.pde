class labels{
  
  Textlabel matWLabel, pDiamLabel, spcLabel, 
            advLabel, onDieLabel, numSlitsLabel, 
            slitWidthLabel, numberOnDie, dieWidthLabel,
            numberOfSlits, slitWidth, dieWidth;
            
  float initial = step / 2;
  
  labels(ControlP5 cp5) {
// material width label      
      matWLabel = cp5.addTextlabel("materialWidthLabel")
                     .setPosition(2*margin, initial)
                     .setText("Material Width")
                     .setColorValue(0)
                     .setFont(font)
                     ;
// part diameter label                     
      pDiamLabel = cp5.addTextlabel("partDiameterLabel")
                      .setPosition(2*margin, initial + step)
                      .setText("Part Diameter")
                      .setColorValue(0)
                      .setFont(font)
                      ;
// die spacing label                      
      spcLabel = cp5.addTextlabel("spacingLabel")
                    .setPosition(2*margin, initial + 2*step)
                    .setText("Spacing")
                    .setColorValue(0)
                    .setFont(font)
                    ;
// value of advance label                    
      advLabel = cp5.addTextlabel("advanceLabel")
                    .setPosition(2*margin, initial + 3*step)
                    .setText("Advance")
                    .setColorValue(0)
                    .setFont(font)
                    ;
// number of cavities on the die label                    
      onDieLabel = cp5.addTextlabel("onDieLabel")
                      .setPosition(2*margin, initial + 4*step)
                      .setText("Cavities")
                      .setColorValue(0)
                      .setFont(font)
                      ;
      numberOnDie = cp5.addTextlabel("numberOnDie")
                       .setPosition(width/4 - margin - 50, 5*step - 3*margin - 5)
                       .setText(str(cavs))
                       .setColorValue(0)
                       .setFont(font)
                       ;
// number of material slits label                      
      numSlitsLabel = cp5.addTextlabel("numberOfSlitsLabel")
                         .setPosition(2*margin, initial + 4*step + 60)
                         .setText("Number of Slits")
                         .setColorValue(0)
                         .setFont(font2)
                         ;
      numberOfSlits = cp5.addTextlabel("numberOfSlits")
                         .setText("-")
                         .setFont(font2)
                         .setColorValue(0)
                         .setPosition(width/4 - 100, initial + 4*step + 60);
// slit width label                         
      slitWidthLabel = cp5.addTextlabel("slitWidthLabel")
                          .setPosition(2*margin, initial + 5*step + 25)
                          .setText("Slit Width")
                          .setColorValue(0)
                          .setFont(font2)
                          ;
      slitWidth = cp5.addTextlabel("slitWidth")
                     .setPosition(width/4 - 100, initial + 5*step + 25)
                     .setText("-")
                     .setColorValue(0)
                     .setFont(font2);
// die width label                         
      dieWidthLabel = cp5.addTextlabel("dieWidthLabel")
                          .setPosition(2*margin, initial + 5*step + 75)
                          .setText("Die Width")
                          .setColorValue(0)
                          .setFont(font2)
                          ;           
      dieWidth = cp5.addTextlabel("dieWidth")
                    .setPosition(width/4 - 100, initial + 5*step + 75)
                    .setText("-")
                    .setColorValue(0)
                    .setFont(font2);
  }
  
}