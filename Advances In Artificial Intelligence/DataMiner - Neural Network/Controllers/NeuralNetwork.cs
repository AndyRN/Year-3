using DataMiner.Classes;
using DataMiner.Helpers;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataMiner.Controllers
{
    public class NeuralNetwork
    {
        private double[] Inputs;
        private List<Node> HiddenLayer;
        private double[] HiddenLayerSums;
        private Node Output;

        public NeuralNetwork(double[] inputs, double[] solution)
        {
            this.Inputs = inputs;
            this.HiddenLayer = new List<Node>();
            this.HiddenLayerSums = new double[Config.NumberOfHiddenNodes];
            this.Output = new Node();

            double[] hiddenWeights = solution.GetRange(0, Config.NumberOfInputs * Config.NumberOfHiddenNodes);
            double[] hiddenBiases = solution.GetRange(hiddenWeights.Length, Config.NumberOfHiddenNodes);
            this.Output.Weights = solution.GetRange(hiddenWeights.Length + hiddenBiases.Length, Config.NumberOfHiddenNodes);
            this.Output.Bias = solution[solution.Length - 1];

            for (int i = 0; i < Config.NumberOfHiddenNodes; i++)
            {
                Node node = new Node
                {
                    Weights = hiddenWeights.GetRange(i * Config.NumberOfInputs, Config.NumberOfInputs),
                    Bias = hiddenBiases[i]
                };

                this.HiddenLayer.Add(node);
            }
        }

        public double CalculateOutput()
        {
            // For each node in the hidden layer.
            for (int i = 0; i < Config.NumberOfHiddenNodes; i++)
            {
                // Calculate the inputs from the input nodes.
                this.HiddenLayerSums[i] = HiddenLayer[i].Calculate(this.Inputs);
            }

            // Calculate the inputs from the hidden nodes.
            return this.Output.Calculate(this.HiddenLayerSums.ToArray());
        }
    }
}
