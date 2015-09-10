#Android Video IP Application
*Android interface for controlling Franken-scooter*

*Author/Maintainer:* James Kuczynski,  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Undergraduate Researcher,  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[Robotics Laboratory][5],  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[University of Massachusetts Lowell][4].  
*Email:* jkuczyns@cs.uml.edu

=====


###**Index**

- [Project Synopsis](#project-synopsis)
- [Dependencies](#dependencies)
- [Install](#install)
- [Fugure Work](#future-work)


###**Project Synopsis**

This package provides a UI to control a robot (specifically, Frankin-scooter) with a Android tablet or smartphone.


###**Dependencies**

- [ROS][1]
- [OpenCV][2]
- [PCL][3] >= 1.8
- [pcl_ros][3] Jade
- Franken-scooter 
- An Android device (or emulator)

###**Install**

```
roslaunch openni2_launch openni2_launch
rosrun web_video_server web_video_server
#rosrun LR frames sender node
 
```


###**Future Work**

The idea of controling a robot via a smartphone or tablet is not an entirely new one; however, little research has been done in this area.  Therefore, it would be interesting to modify this package to make it more generalized to work with other robots.  I will make these changes within the next year.


[1]: http://www.ros.org/
[2]: http://opencv.org/
[3]: http://pointclouds.org/
[4]: http://www.uml.edu/
[5]: http://robotics.cs.uml.edu/

