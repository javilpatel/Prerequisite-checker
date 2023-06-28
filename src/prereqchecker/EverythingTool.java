package prereqchecker;
import java.util.*;

public class EverythingTool {
    private  HashMap<String, ArrayList<String>> courses;
    private  HashMap<String, Boolean> marked;
    private  HashSet<String> forcourse;
    private ArrayList<String> classestaken;
    private HashSet<String> classlist;
    
    
    

    public EverythingTool(){
        courses = new HashMap<>();
        marked = new HashMap<>();
        forcourse = new HashSet<>();
        classestaken = new ArrayList<>();
        classlist = new HashSet<>();
        
    }

    public HashMap<Integer, HashSet<String>> SchedulePlan(String classwewant, ArrayList<String> allclasesinAL){

        HashSet<String> Needtotake = WhatClassesNeeded(classwewant, allclasesinAL);     
        int semestercount = 1;
        HashSet<String> containprereqgivencourse = new HashSet<>();
        HashSet<String> holdallthecourseswecantakeinasemester = new HashSet<>();
        HashMap<Integer, HashSet<String>> courselistlele = new HashMap<>();
        


    while (!Needtotake.isEmpty()){
        for (String courseId : Needtotake){
            containprereqgivencourse = WhatClassesNeeded(courseId, allclasesinAL);
            
            if (containprereqgivencourse.isEmpty()){
                
                holdallthecourseswecantakeinasemester.add(courseId);
                
            }
        }

        if(!holdallthecourseswecantakeinasemester.isEmpty()){
            
            HashSet<String> deepcopy = new HashSet<>();
            for (String course: holdallthecourseswecantakeinasemester){
                deepcopy.add(course);
            }


            courselistlele.put(semestercount, deepcopy);

            

            
            semestercount++;
            Needtotake.removeAll(deepcopy);
            for (String courseid : deepcopy){
                allclasesinAL.add(courseid);
                
            } 
            holdallthecourseswecantakeinasemester.clear();


            
        }
   
    }

   



    return courselistlele;
    }

    


    public HashSet<String> ClassesTakenList(ArrayList<String> classlistye) { //THIS METHOD SMTH WRONG
        classlist = new HashSet<>();

        for (int i=0; i< classlistye.size();i++){
            classlist.add(classlistye.get(i));  

            HashSet<String> tempget = prereqgetter(classlistye.get(i));
            
            classlist.addAll(tempget);
        }



        return classlist;
    }

    public ArrayList<String> EligibleToTake(HashSet<String> classestook, HashMap<String, ArrayList<String>> courses){
        ArrayList<String> listabletotake = new ArrayList<>();

        for (String courseID : courses.keySet()){

            if(!classestook.contains(courseID)){
                

                if (CanITake(classestook, courseID)==true){
                    
                    listabletotake.add(courseID);
                }
            }
        }
        return listabletotake;
    }

    public Boolean CanITake(HashSet<String> classestook, String CourseId){
        HashSet<String> prereqsofcourse = prereqgetter(CourseId);

        for (String course: prereqsofcourse){
            if (!classestook.contains(course)){
                return false;
            }
        }

        return true;
    }

   

    public  HashSet<String> prereqgetter(String courseID){
        forcourse = new HashSet<>();
        
        
        ArrayList<String> getprereq = new ArrayList<>();
        getprereq = courses.get(courseID);
       

        for (int i=0; i<getprereq.size();i++){
            forcourse.add(getprereq.get(i));
                
            DFS(getprereq.get(i), forcourse);
        }

        setforcourse(forcourse);
         
        return forcourse;
    }
    public void DFS(String courseID, HashSet<String> forcourse){
         marked = new HashMap<>();
         falsesetter(courses);
        ArrayList<String> getlist = courses.get(courseID);
        for (int i=0; i< getlist.size(); i++){
            if (marked.get(getlist.get(i))==false){
                forcourse.add(getlist.get(i));
                DFS(getlist.get(i), forcourse);
            }
        }

    }

    public void falsesetter(HashMap<String, ArrayList<String>> courses){
        for (String course : courses.keySet()){
            ArrayList<String> getit = courses.get(course);
            for (int j=0; j<getit.size();j++){
                marked.put(getit.get(j), false);
            }
        }
    }

    public HashSet<String> WhatClassesNeeded(String coursewant, ArrayList<String> classestaken ){
        HashSet<String> coursesoverall = prereqgetter(coursewant);
        HashSet<String> coursestaken = new HashSet<>();
        HashSet<String> classesneeded = new HashSet<>();

        for (int i=0; i<classestaken.size();i++){
            HashSet<String> temp = prereqgetter(classestaken.get(i));
            temp.add(classestaken.get(i));
            coursestaken.addAll(temp);
        }

        for (String courseID : coursesoverall){
            if (!coursestaken.contains(courseID)){
                classesneeded.add(courseID);
            }
        }
        return classesneeded;
    }
    
    


        

        
        
        
        //You want to create something that, for every class, you add all of its prereqs (double for loop?)
        //USE HASHSET.ADDALL FROM PREVIOUS HASHSET.
        
    




    public ArrayList<String> addClass(String course){
        classestaken.add(course);
        return classestaken;

    }
    public ArrayList<String> setClassTaken(ArrayList<String> classestakn){
        classestaken = classestakn;
        return classestaken;
    }

    public ArrayList<String> getClassestaken(){
        return classestaken;
    }

    public HashMap<String, ArrayList<String>> getCourses(){
        return courses;
    }
    public HashMap<String, Boolean> getmarked(){
        return marked;
    }
    public HashSet<String> getforcourse(){
        return forcourse;
    }
    public HashSet<String> setforcourse(HashSet<String> forcours){
        forcourse = forcours;
        return forcourse;
    }


    public HashMap<String, ArrayList<String>> setCourses(HashMap<String, ArrayList<String>> course){
        course = courses;
        return course;
    }

    public  HashMap<String, ArrayList<String>> createCourse(){
        int coursenumbers = StdIn.readInt();
        for (int i=0; i<coursenumbers;i++){
            courses.put(StdIn.readString(), new ArrayList<>());
        }
        int prereqs = StdIn.readInt();
        for (int i =0; i<prereqs; i++){
            String courseID = StdIn.readString();
            ArrayList<String> prerequs = courses.get(courseID);
            prerequs.add(StdIn.readString());
            courses.put(courseID,prerequs);
        }
        return courses;
    }

    



}
