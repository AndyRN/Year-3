using DataMiner.Controllers;
using DataMiner.Helpers;
using System;
using System.Collections.Generic;
using System.Linq;

namespace DataMiner.Classes
{
    public class Individual
    {
        public double[] Chromosome { get; set; }
        public int Fitness { get; set; }
        public double SuccessRate { get; set; }

        public Individual()
        {
            this.SuccessRate = 0;

            this.Chromosome = new double[Config.ChromosomeLength];
            for (int i = 0; i < Config.ChromosomeLength; i++)
            {
                // Can only be between -1.0 or 1.0.
                this.Chromosome[i] = RandomNumber.GenerateDouble();
            }
        }

        public Individual(double[] chromosome)
        {
            this.UpdateChromosome(chromosome);
        }

        public void UpdateChromosome(double[] chromosome)
        {
            this.Chromosome = chromosome;
        }

        public Individual Clone()
        {
            return new Individual
            {
                Fitness = this.Fitness,
                Chromosome = this.Chromosome
            };
        }

        public void EvaluateFitness()
        {
            this.Fitness = 0;

            foreach (string dataLine in Config.LearningData)
            {
                double[] dataBits = DataConverter.Convert(dataLine);

                NeuralNetwork nn = new NeuralNetwork(dataBits.GetRange(0, Config.RuleLength - 1), this.Chromosome);

                double output = nn.CalculateOutput();

                if (Math.Round(output) == dataBits[Config.RuleLength - 1])
                {
                    this.Fitness++;
                }
            }
        }
    }
}
