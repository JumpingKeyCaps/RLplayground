# RLplayground — A Reinforcement Learning Playground for Self-Learning Agents



  ![Status](https://img.shields.io/badge/status-WIP-red)
  ![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF?logo=kotlin&logoColor=white)
  ![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-4285F4?logo=android&logoColor=white)
  ![Android Studio](https://img.shields.io/badge/Android%20Studio-3DDC84?logo=androidstudio&logoColor=white)
  ![Reinforcement Learning](https://img.shields.io/badge/Reinforcement%20Learning-blue)
  ![MVVM](https://img.shields.io/badge/Architecture-MVVM-blueviolet)
  ![Coroutines](https://img.shields.io/badge/Coroutines-0095D5?logo=kotlin&logoColor=white)
  ![Educational](https://img.shields.io/badge/Goal-Educational-ff69b4)

---

## 🏆 Project Goal

This project is an educational exploration and demonstration of Reinforcement Learning (RL) concepts through a simple but meaningful interactive simulation.  
The main objective is **to build a flexible Android app in Kotlin/Compose where a virtual agent learns to intercept ballistic missiles**, protecting a simulated city using limited defensive resources and learning optimal interception strategies over time through trial and error, improving its policy over many iterations using different RL algorithms.

This project aims to:  
- Provide a hands-on, visual playground for understanding core RL principles  
- Compare multiple RL strategies side by side on the same problem  
- Show real-time learning dynamics with detailed metrics and visualizations  
- Serve as a solid foundation to RL skills and extend to more complex problems later

---

## 💡 Motivation

The field of Reinforcement Learning is both fascinating and challenging, bridging the gap between theory and practical autonomous decision-making.  
As an Android developer keen on expanding my skills in machine learning and AI, I wanted a project that:  
- Is **hands-on and visual**, helping me truly grasp RL dynamics  
- Covers multiple fundamental RL algorithms to understand their strengths and weaknesses  
- Offers real-time feedback on learning progress through intuitive visualizations  
- Can serve as a **playground to experiment and grow**, with potential extensions beyond simple environments  
- Is fully implemented in Kotlin/Compose, showcasing modern Android tech with a deep learning twist  

This project is my stepping stone towards Reinforcement Learning, combining software craftsmanship with AI exploration.


---

## ⚙️ Key Concepts

- **Environment**: 2D physics-based battlefield with incoming threats and interceptor missiles  
- **Agent**: Controls the anti-missile battery (DCA) — decides when and where to fire  
- **Constraints**: Limited missiles, cooldowns, and multiple targets  
- **Rewards**: Intercepting missiles yields positive reward; letting them hit the city triggers penalties  
- **Learning**: The agent must learn timing, aiming, and strategic targeting over many episodes  
- **Visualization**: Real-time simulation of missile paths, interceptions, rewards, and decision metrics  

---

## 🎯 Reinforcement Learning Strategies Implemented

### 1. **Q-Learning**

- **Concept**:  
  Q-Learning is an off-policy, value-based RL method that learns a table of Q-values \( Q(s,a) \), representing the expected cumulative reward of taking action \( a \) in state \( s \).  
  The agent updates Q-values using the Bellman equation by observing rewards and next states, learning an optimal policy by greedily selecting actions with the highest Q-values.

- **How it works**:  
 ```
Q(s, a) = Q(s, a) + α [r + γ * max(Q(s', a')) - Q(s, a)]
 ```
where `α` is learning rate, `γ` is discount factor, `r` is reward, and `s'` is the next state.

- **Limitations**:  
  Requires discrete, manageable state and action spaces. For continuous domains, discretization or function approximation is needed, which can reduce precision or increase complexity.

---

### 2. **SARSA (State-Action-Reward-State-Action)**

- **Concept**:  
  SARSA is an on-policy, value-based RL method similar to Q-Learning, but updates are based on the actual next action taken by the current policy, not the max action.  
  This makes it more conservative and often safer in some environments.

- **Update rule**:  
 ```
Q(s, a) = Q(s, a) + α [r + γ * Q(s', a') - Q(s, a)]
 ```
  where `a'` is the action taken in state `s'`.

- **Limitations**:  
  Same as Q-Learning — depends on discrete states and actions; convergence depends on exploration policies.

---


## 🕹️ The Simulation — Iron Dome Physics Environment

### Key Physical Parameters

| Parameter               | Default Value     | Description                                 |
|------------------------|-------------------|---------------------------------------------|
| Gravity                | 9.8 px/s²         | Affects all projectiles                     |
| Missile velocity       | Variable          | Enemy missiles can be slow or fast          |
| Cooldown between shots | 2 seconds         | Forces agent to make careful decisions      |
| Missile stock limit    | 3–5 missiles      | Resource management becomes strategic       |
| Delta Time             | ~16 ms (60 FPS)   | Real-time simulation update interval        |

### Simulation Elements

- **Incoming Missiles**: Fall in parabolic trajectories from the left side  
- **City**: On the right — must be protected at all costs  
- **Interceptor Battery (DCA)**: Central position — can fire at variable angles  
- **Explosions**: Triggered on intercept or ground impact  
- **Reward System**:  
  - +1 for intercept  
  - -2 if city hit  
  - -0.2 for wasted shot  

---

## 📊 Real-Time Visualization & Monitoring

- Live missile & interceptor trajectories  
- Explosions and intercept effects  
- Heatmap of missile impact zones  
- Reward chart over time  
- Cooldown & ammo indicators  
- Policy visualizations for actor/critic and REINFORCE  
- Q-value grids or action preference overlays  
- Episode/iteration counters and learning curves  
- Controls: pause, speed, reset, strategy selector

---

## 🛠️ Project Architecture

- `DefenseEnvironment`: Simulates physics, missile logic, intercept checks  
- `AgentController`: Implements various RL strategies  
- `SimulationManager`: Ties environment, agent, and loop execution  
- `UI`: Built with Jetpack Compose to render simulation and analytics  
- `VisualizationTools`: Charts, overlays, and metrics

---

## 🚀 Development Roadmap

1.  Create logic of the 2D missile interception engine  
2.  Basic environment: gravity, projectile logic, collisions  
3.  Random agent to test mechanics  
4.  Implement Q-Learning and SARSA with discretized grid  
5.  Add real-time visualization and UI overlays  
6.  Integrate DQN with neural network approximation  
7.  Implement REINFORCE for stochastic aiming  
8.  Build Actor-Critic for long-term reward management  
9.  Add adaptive difficulty and missile waves  
10. Polish UI, animations, and exportable logs  

---

## 🚀 Next Steps

- Implement environment physics and playground UI in Compose  
- Code baseline Random and Q-Learning agents  
- Add UI for monitoring RL metrics in real-time  
- Explore extensions with obstacles or more complex tasks  

---

> *This project is a continuous journey into Reinforcement Learning, blending practical Android development with AI concepts.  
