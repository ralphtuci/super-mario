package com.example.supermarobros;

public class PhysicsEngine {
  private int gravity;
  private int friction;
  private int verticalForce;
  private int horizontalForce;
  public PhysicsEngine () {
    initPhysicsEngine();
  }
  private void initPhysicsEngine() {
    gravity = 10;
    friction = 10;
    verticalForce = 30;
    horizontalForce = 30;
  }
}
