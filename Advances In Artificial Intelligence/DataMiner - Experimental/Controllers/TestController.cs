using DataMiner.Classes;
using DataMiner.Helpers;
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

            double[] dataValues = DataConverter.Convert(dataLine);

            // If the solution correctly predicts the output, increment it's successes by 1.
            if (IsSuccessful(dataValues))
            {
                this.TotalSucceeded++;
            }
        }

        private bool IsSuccessful(double[] dataValues)
        {
            for (int i = 0; i < Config.RulesPerIndividual; i++)
            {
                int[] rule = this.BestSolution.GetRule(i);

                // Check to see if the solution has any rules that match the current line of data.
                if (this.BestSolution.IsMatch(rule, dataValues))
                {
                    // Return true if the predicted output matches the actual output.
                    return rule[Config.RuleLength - 1] == dataValues[Config.RuleLength - 1];
                }
            }

            return false;
        }
    }
}
