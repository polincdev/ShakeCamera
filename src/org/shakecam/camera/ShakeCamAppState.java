/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.shakecam.camera;

import com.jme3.app.Application;
 
import com.jme3.app.state.BaseAppState;
 
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
 
import java.util.Random;
 

/**
 *
 * @author xxx
 */
public class ShakeCamAppState extends BaseAppState{
  
    Camera cam;
    
    
    boolean enabled=false;
    float[] samples;
    
    Random rand = new Random();
    float internalTimer = 0;
    float shakeDuration = 0;

    float period = 5; // In milis, make longer if you want more variation
    int frequency = 35; // hertz
    float amplitude = 20; // how much you want to shake
    boolean falloff = true; // if the shake should decay as it expires
    int direction=0; //how to shake
    int sampleCount;
    
        
    Vector3f addValue=new Vector3f();
    Vector3f transAddValue=new Vector3f();
    
    //Shake directions
    public static int SHAKE_DIR_ALL=3;
    public static int SHAKE_DIR_LEFT=5;
    public static int SHAKE_DIR_RIGHT=7;
    public static int SHAKE_DIR_UP=11;
    public static int SHAKE_DIR_DOWN=13;
    public static int SHAKE_DIR_FRONT=17;
    public static int SHAKE_DIR_BACK=19;
    public static int SHAKE_DIR_HORIZONTAL=23;
    public static int SHAKE_DIR_VERTICAL=29;
    public static int SHAKE_DIR_STRAIGHT=31;

   public ShakeCamAppState(  Camera cam )
   {
        this.cam=cam;
    
   }
   
   

    public void update(float dt ) {
        
        if(!enabled)
            return;
        
        internalTimer += dt;
        if (internalTimer > period) 
            internalTimer -= period;
         
        if (shakeDuration > 0) {
            shakeDuration -= dt;
            
             
        float shakeTime = (internalTimer * frequency);
        int first = (int) shakeTime  % sampleCount;
        int second = (first + 1) % sampleCount;
        int third = (first + 2) % sampleCount;
        float deltaT = shakeTime - (int) shakeTime;
       // System.out.println("CAM="+amplitude+" "+falloff+" "+Math.min(shakeDuration, 1f)+" "+first+" "+deltaT+" "+second+" "+samples.length+" "+internalTimer+" "+frequency);
        float delX =  samples[first] * deltaT + samples[second] * (1f - deltaT);
        float delY = samples[second] * deltaT + samples[third] * (1f - deltaT);
        float delZ = samples[third] * deltaT + samples[second] * (1f - deltaT);
        float deltaX = 0;
        float deltaY = 0;
        float deltaZ = 0;
        //
         if(direction%SHAKE_DIR_ALL==0)
           {
            deltaX = delX;
            deltaY = delY;
            deltaZ = delZ;
           }
        else if(direction%SHAKE_DIR_HORIZONTAL==0)
             deltaX = delX;
        else if(direction%SHAKE_DIR_VERTICAL==0)
             deltaY = delY;
        else if( direction%SHAKE_DIR_STRAIGHT==0)
            deltaZ = delZ;
         //
        if(direction%SHAKE_DIR_LEFT==0)
             deltaX = Math.abs(delX);
        else if(direction%SHAKE_DIR_RIGHT==0)
              deltaX=-Math.abs(delX);
        //
          if(direction%SHAKE_DIR_UP==0)
               deltaY = Math.abs(delY);
         else if(direction%SHAKE_DIR_DOWN==0)
              deltaY = -Math.abs(delY);
         if(direction%SHAKE_DIR_FRONT==0)
               deltaZ = Math.abs(delZ);
         else if(direction%SHAKE_DIR_BACK==0)
               deltaZ = -Math.abs(delZ);

          // System.out.println("CAM2="+deltaX+" "+deltaY+" "+ deltaZ);

        float x= deltaX * amplitude * (falloff ? Math.min(shakeDuration, 1f) : 1f);
        float y = deltaY * amplitude * (falloff ? Math.min(shakeDuration, 1f) : 1f);
        float z = deltaZ  * amplitude * (falloff ? Math.min(shakeDuration, 1f) : 1f);
        addValue.set(x, y, z);
        cam.getRotation().mult(addValue, transAddValue);
        cam.setLocation(cam.getLocation().add(transAddValue));        
         }
     else
        {
            enabled=false;
        }
    }
	


    @Override
    protected void initialize(Application app) {
     }

    @Override
    protected void cleanup(Application app) {
     }

    @Override
    protected void onEnable() {
     }

    @Override
    protected void onDisable() {
     }
   
     /**
     * Main, all purpose shake method  
     * @param shakeDuration  duration of the shake in seconds
     * @param period in milis, period/shake change witin duration. It should be much less than shakeDuration
     * @param frequency  hertz
     * @param amplitude  how much you want to shake
     * @param falloff if the shake should decay as it expires
     * @param equalShake if the shake is allways of the same value and thus doesnt change cam position. True for less real, but stable shake
     * @param direction - shake direction - predefined
     * 
     */
    public void shake(float shakeDuration, float period, int frequency, float amplitude, boolean falloff, boolean equalShake, int direction) {
        this.shakeDuration = shakeDuration;
        this.period = period;
        this.frequency = frequency;
        this.amplitude = amplitude;
        this.falloff = falloff;
        this.direction=direction;
       
        this.enabled=false;
        this.sampleCount =  this.frequency;
        this.samples = new float[this.sampleCount];
        boolean dir=true;
        for (int i = 0; i < this.sampleCount; i++) 
             {
              float var=rand.nextFloat();   
              if(equalShake)
                  this.samples[i] = 0.5f;
              else            
                  this.samples[i] = var>0.5f ? (var-0.5f):var ;
              if(!dir)   
                this.samples[i]=-this.samples[i];
               dir=!dir;
             }
      this.enabled=true;
    }
      
     /**
     * Stops shaking. Used mainly with long term shaking
      */
     public void stopShake()
	{
	   this.enabled=false;
	}
    	
public	void shakeHitSoft()
	{
	shake(0.5f,0.1f, 14, 0.02f, true,false, SHAKE_DIR_ALL);
	}
public	void shakeHitMedium()
	{
	shake(0.9f,0.2f, 24, 0.05f, true,false,SHAKE_DIR_ALL);
	}
public	void shakeHitHard()
	{
	shake(1,0.3f, 35, 0.09f, true,false,SHAKE_DIR_ALL);
	}
 public	void shakeDriveSlow()
	{
	shake(1000000000f,10f,  12, 0.002f, true, false,SHAKE_DIR_ALL);
	}
 public	void shakeDriveFast()
	{
	shake(1000000000f,10f,  16, 0.0023f, true, false,SHAKE_DIR_ALL);
	}
}