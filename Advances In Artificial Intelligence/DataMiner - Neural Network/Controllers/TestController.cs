using DataMiner.Classes;
using DataMiner.Helpers;
using System;
using System.Collections.Generic;
using System.Linq;

namespace DataMiner.Controllers
{
    public class TestController
    {
        private Individual BestSolution;
        private double TotalTests;
        private double TotalSucceeded;

        public TestController(Individual bestSolution)
        {
            this.BestSolution = bestSolution;

            this.TotalTests = 0;
            this.TotalSucceeded = 0;
        }

        public double CalculateSuccessRate()
        {
            return (this.TotalSucceeded / this.TotalTests) * 100;
        }

        public void TestSolution(string dataLine)
        {
            TotalTests++;

            double[] dataBits = DataConverter.Convert(dataLine);

            NeuralNetwork nn = new NeuralNetwork(dataBits.GetRange(0, Config.RuleLength - 1), this.BestSolution.Chromosome);

            double output = nn.CalculateOutput();

            if (Math.Round(output) == dataBits[Config.RuleLength - 1])
            {
                this.TotalSucceeded++;
            }
        }
    }
}
