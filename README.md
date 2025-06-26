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
The main objective is **to build a flexible Android app in Kotlin/Compose where a virtual agent learns how to throw a bouncing ball into a cup through trial and error, improving its policy over many iterations using different RL algorithms.**

Beyond the bouncing ball, this project aims to:  
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

- **Environment**: A simple 2D physics simulation with gravity, bounces, obstacles, and a target cup  
- **Agent**: Learns a throwing policy (angle, power) to maximize success  
- **Rewards**: Designed to encourage landing the ball in the cup and penalize misses  
- **Learning**: Agent updates its policy based on feedback from environment interactions  
- **Visualization**: Real-time trajectories, heatmaps, and learning metrics to track progress

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

### 3. **Deep Q-Network (DQN)**

- **Concept**:  
  To handle large or continuous state spaces, DQN replaces the Q-table with a deep neural network approximating `Q(s,a;θ)`.  
  It learns by minimizing the difference between predicted Q-values and target Q-values derived from observed rewards.

- **Key techniques**:  
  - **Experience Replay**: Stores past experiences to break correlations and improve training stability.  
  - **Target Network**: A separate network to generate stable target Q-values, updated periodically.

- **Challenges**:  
  Requires careful tuning, sufficient compute, and additional components (replay buffer, batch training). Implementation complexity rises significantly.

---

### 4. **Policy Gradient (REINFORCE)**

- **Concept**:  
  Instead of learning value functions, policy gradient methods optimize the policy `π(a∣s;θ)` directly by maximizing expected reward.  
  REINFORCE uses Monte Carlo methods to estimate gradients of the expected return and updates policy parameters via stochastic gradient ascent.

- **Update formula**:  
 ```
Δθ = α * ∑ ( ∇θ log π(a_t | s_t; θ) * G_t )
```
  where `G_t` is the return from timestep `t`.

- **Advantages**:  
  Naturally handles continuous action spaces and stochastic policies.

- **Limitations**:  
  High variance in updates leads to slow convergence; requires variance reduction techniques (baseline subtraction, advantage functions).

---

### 5. **Actor-Critic**

- **Concept**:  
  Combines policy gradient (actor) with a value function (critic) estimating expected returns to reduce variance and improve learning stability.  
  The actor updates policy parameters using gradients weighted by the critic's evaluation, while the critic updates its value estimate to better predict returns.

- **Benefits**:  
  More sample efficient and stable than pure policy gradient methods.

- **Complexity**:  
  Requires maintaining and training two models (actor and critic); implementation is more involved.

---

## 🕹️ The Playground - 2D Physics Environment

### Key Physical Parameters

| Parameter           | Default Value    | Description                          |
|---------------------|------------------|------------------------------------|
| Gravity (g)         | 9.8 px/s²        | Constant vertical acceleration     |
| Bounce coefficient  | 0.7              | Fraction of velocity conserved after bounce (0 < e < 1) |
| Friction (optional) | 0.02             | Horizontal speed decay over time   |
| Delta Time          | ~16 ms (60 FPS)  | Physics update interval             |

### Environment Elements

- **Ball**: 2D position, velocity, and acceleration vectors  
- **Obstacles**: Static rectangular shapes with collision detection  
- **Cup (target)**: Fixed zone that defines success if ball lands inside  
- **Field boundaries**: Walls and floor with bounce logic or reset conditions

---

## 📊 Real-Time Visualization & Monitoring

### UI Information Displayed

- Live ball trajectory path with fading trail  
- Throw history visualization with color-coded success/failure (ghost throws)  
- Impact heatmap showing frequently landed zones  
- Reward over time graph showing learning progress  
- Iteration/episode count  
- Running average and variance of recent rewards  
- Exploration rate (epsilon) for ε-greedy policies  
- Policy visualization (action probability distributions) for policy gradient and actor-critic methods  
- Q-value heatmaps or tables for discrete methods  
- Controls: start, pause, reset simulation  
- Strategy selector dropdown  

---

## 🛠️ Project Architecture

- `Environment` module: physics simulation, collision, reward calculation  
- `Agent` module: RL algorithms implementing decision and learning steps  
- `SimulationManager`: Orchestrates interaction loops and episode handling  
- `UI` with Jetpack Compose: physics visualization, metrics, and controls  
- `DataVisualization`: real-time charts, heatmaps, logs  

---

## 🚀 Development Roadmap

1. Build the physics environment with gravity, bouncing ball, and cup target  
2. Implement random agent baseline for testing environment  
3. Implement Q-Learning and SARSA with discrete state/action representation  
4. Add throw visualization, heatmaps, and reward graphs  
5. Develop Policy Gradient (REINFORCE) algorithm for continuous action spaces  
6. Build Actor-Critic method with separate policy and value networks  
7. Optionally extend environment with obstacles and friction  
8. Add modular RL strategy selector and UI enhancements  
9. Optimize performance and polish UI/UX  
10. Write documentation and prepare portfolio demo  

---

## 📚 Useful Resources

- [Kotlin Coroutines Guide](https://kotlinlang.org/docs/coroutines-guide.html)  
- [Deep Reinforcement Learning with TensorFlow](https://www.tensorflow.org/agents)  

---

## 🚀 Next Steps

- Implement environment physics and playground UI in Compose  
- Code baseline Random and Q-Learning agents  
- Add UI for monitoring RL metrics in real-time  
- Progressively implement SARSA, DQN, Policy Gradient, and Actor-Critic algorithms  
- Explore extensions with obstacles or more complex tasks  

---

> *This project is a continuous journey into Reinforcement Learning, blending practical Android development with AI concepts.  
