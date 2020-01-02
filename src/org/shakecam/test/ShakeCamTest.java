package org.shakecam.test;

import com.jme3.app.SimpleApplication;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Quad;
import org.shakecam.camera.ShakeCamAppState;
import static org.shakecam.camera.ShakeCamAppState.SHAKE_DIR_ALL;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * @author normenhansen
 */
public class ShakeCamTest extends SimpleApplication implements ActionListener {

       
  BitmapText hintText;  
  BitmapText debugText; 
  ShakeCamAppState camAppState;
   int currentShake=0;
  String[] names=new String[]{
        "ALL",
        "Left",
        "Right",
       "Up",
       "Down",
       "Front",
       "Back",
       "Horizontal",
       "Vertical",
       "Straight",
   };
   int[] types=new int[]{
        ShakeCamAppState.SHAKE_DIR_ALL,
        ShakeCamAppState.SHAKE_DIR_LEFT,
        ShakeCamAppState.SHAKE_DIR_RIGHT,
        ShakeCamAppState.SHAKE_DIR_UP,
        ShakeCamAppState.SHAKE_DIR_DOWN,
        ShakeCamAppState.SHAKE_DIR_FRONT,
        ShakeCamAppState.SHAKE_DIR_BACK,
        ShakeCamAppState.SHAKE_DIR_HORIZONTAL,
        ShakeCamAppState.SHAKE_DIR_VERTICAL,
        ShakeCamAppState.SHAKE_DIR_STRAIGHT,
      
  };
    float shakeDuration =0.5f;
    float period = 0.1f;  
    int frequency = 20;  
    float amplitude = 0.02f;  
 
   public static void main(String[] args) {
        ShakeCamTest app = new ShakeCamTest();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        Box b = new Box(1, 1, 1);
        Geometry geom = new Geometry("Box", b);
        Geometry geom2 = new Geometry("Quad",new Quad(15,15));
        geom2.rotate(-90*FastMath.DEG_TO_RAD,0,0);
        geom2.move(-7,-1, 7);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        geom.setMaterial(mat);
        Material mat2 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat2.setColor("Color", ColorRGBA.Green);
        geom2.setMaterial(mat2);

        rootNode.attachChild(geom);
         rootNode.attachChild(geom2);
        
          //Text
        BitmapFont font =  getAssetManager().loadFont("Interface/Fonts/Default.fnt");
	//Hint
	hintText = new BitmapText(font);
	hintText.setSize(font.getCharSet().getRenderedSize()*1.5f);
	hintText.setColor(ColorRGBA.Red);
	hintText.setText("Trigger:SPACE Dur:1/2 Period:3/4 Freq:5/6 Ampl:7/8 Dir:-/+");
        
      
	hintText.setLocalTranslation(0, this.getCamera().getHeight()-10, 1.0f);
	hintText.updateGeometricState();
        guiNode.attachChild(hintText);
        //Info
	debugText=hintText.clone();
        debugText.setColor(ColorRGBA.White);
	debugText.setText("Dur:"+shakeDuration+" Period:"+period+" Freq:"+frequency+" Ampl:"+amplitude+" Dir type:"+names[currentShake] );
	debugText.setLocalTranslation(0, hintText.getLocalTranslation().y-30, 1.0f);
	debugText.updateGeometricState();
        guiNode.attachChild(debugText);
        
          //Keys
        inputManager.addMapping("Shake", new KeyTrigger(KeyInput.KEY_SPACE));
         inputManager.addMapping("TypeMin", new KeyTrigger(KeyInput.KEY_MINUS));
         inputManager.addMapping("TypePls", new KeyTrigger(KeyInput.KEY_EQUALS));
         inputManager.addMapping("DurM", new KeyTrigger(KeyInput.KEY_1));
         inputManager.addMapping("DurP", new KeyTrigger(KeyInput.KEY_2));
         inputManager.addMapping("PerM", new KeyTrigger(KeyInput.KEY_3));
         inputManager.addMapping("PerP", new KeyTrigger(KeyInput.KEY_4));
         inputManager.addMapping("FreqM", new KeyTrigger(KeyInput.KEY_5));
         inputManager.addMapping("FreqP", new KeyTrigger(KeyInput.KEY_6));
         inputManager.addMapping("AmplM", new KeyTrigger(KeyInput.KEY_7));
         inputManager.addMapping("AmplP", new KeyTrigger(KeyInput.KEY_8));
         inputManager.addListener(this, new String[]{"Shake"});
         inputManager.addListener(this, new String[]{"TypeMin"});
         inputManager.addListener(this, new String[]{"TypePls"});
          inputManager.addListener(this, new String[]{"DurM"});
         inputManager.addListener(this, new String[]{"DurP"});
          inputManager.addListener(this, new String[]{"PerM"});
         inputManager.addListener(this, new String[]{"PerP"});
          inputManager.addListener(this, new String[]{"FreqM"});
         inputManager.addListener(this, new String[]{"FreqP"});
          inputManager.addListener(this, new String[]{"AmplM"});
         inputManager.addListener(this, new String[]{"AmplP"});
         
         
         
         //
         camAppState=new ShakeCamAppState(cam);
         stateManager.attach(camAppState);
         
         
    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
    
    
    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
       
        
        if(!isPressed)
            return;
       
      if(name.equals("Shake"))
        {
           
           camAppState.shake(shakeDuration,period, frequency, amplitude, true, false, types[currentShake]);
         }
     else if(name.equals("TypeMin"))
        {
             currentShake--;
             if(currentShake<0)
                 currentShake=0;
         }    
     else if(name.equals("TypePls"))
        {
             currentShake++;
             if(currentShake>types.length-1)
                 currentShake=types.length-1;
         }    
        else if(name.equals("DurM"))
        {
             shakeDuration-=0.1f;
             if(shakeDuration<0)
                 shakeDuration=0;
         }    
        else if(name.equals("DurP"))
        {
             shakeDuration+=0.1f;
             if(shakeDuration>10)
                 shakeDuration=10;
         }    
       else if(name.equals("PerM"))
        {
             period-=0.1f;
             if(period<0)
                 period=0;
         }    
        else if(name.equals("PerP"))
        {
             period+=0.1f;
             if(period>10)
                 period=10;
         }    
        else if(name.equals("FreqM"))
        {
             frequency-=1;
             if(frequency<0)
                 frequency=0;
         }    
        else if(name.equals("FreqP"))
        {
             frequency+=1f;
             if(frequency>50)
                 frequency=50;
         }    
           else if(name.equals("AmplM"))
        {
             amplitude-=0.01f;
             if(amplitude<0)
                 amplitude=0;
         }    
        else if(name.equals("AmplP"))
        {
             amplitude+=0.01f;
             if(amplitude>10)
                 amplitude=10;
         }    
      
      
      refreshDisplay();
    }
    
    void refreshDisplay()
    {
      debugText.setText("Dur:"+shakeDuration+" Period:"+period+" Freq:"+frequency+" Ampl:"+amplitude+" Dir type:"+names[currentShake] );
	
    }
        
        
}
