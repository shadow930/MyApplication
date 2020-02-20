// ISchool.aidl
package com.example.severdemo;

import com.example.severdemo.Student;

interface ISchool {
          String getSchoolName();
          int getStudentNum();
          Student getStudent();
}
