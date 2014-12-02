using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataMiner.Classes
{
    public class Node
    {
        public double[] Weights { get; set; }
        public double Bias { get; set; }

        public double Calculate(double[] inputs) // (input * weight) + bias
        {
            double sum = 0.0;

            // Multiply each input by their respective weight.
            for (int i = 0; i < inputs.Length; i++)
            {
                sum += inputs[i] * this.Weights[i];
            }

            // Apply the bias to the result.
            sum += this.Bias;

            // Execute the sigmoid function.
            return SigmoidFunction(sum);
        }

        private double SigmoidFunction(double sum)
        {
            if (sum < -45.0)
            {
                return 0.0;
            }
            else if (sum > 45.0)
            {
                return 1.0;
            }
            else
            {
                return 1.0 / (1.0 + Math.Exp((sum)));
            }
        }
    }
}
