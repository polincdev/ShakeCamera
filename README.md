# ShakeCamera
Camera shaking state for JMonkey Game Engine

![ShakeCamera](../master/img/ShakeCamera.jpg)

### Usage
#### Install
```
ShakeCamAppState camAppState=new ShakeCamAppState(cam);
stateManager.attach(camAppState);
```
#### Params
```
//duration of the shake in seconds
float shakeDuration =0.5f;
//period in milis, period/shake change witin duration. It should be much less than shakeDuration
float period = 0.1f;  
//frequency   
int frequency = 20;  
// how much you want to shake
float amplitude = 0.02f;  
//if the shake should decay as it expires
boolean falloff=true;
//if the shake is allways of the same value and thus doesnt change cam position. True for less real, but stable shake
boolean  equalShake =false;
//shake direction - predefined
int directon=ShakeCamAppState.SHAKE_DIR_ALL;
```
#### Call
```
camAppState.shake(shakeDuration,period, frequency, amplitude, falloff, equalShake, directon);
       
```

