using DataMiner.Helpers;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

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
                // If the current value is the last value of a rule.
                if ((i + 1) % Config.RuleLength == 0)
                {
                    // Can only be 0 or 1.
                    this.Chromosome[i] = RandomNumber.Generate(2);
                }
                else
                {
                    // Otherwise can be between 0.0 and 1.0.
                    this.Chromosome[i] = RandomNumber.GenerateDouble();
                }
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

        public double[] GetRule(int ruleNumber)
        {
            return this.Chromosome.GetRange(ruleNumber * Config.RuleLength, Config.RuleLength);
        }

        public void EvaluateFitness()
        {
            this.Fitness = 0;

            // Threading to improve performance.
            Parallel.ForEach(Config.LearningData, dataLine => this.TestEachRule(DataConverter.Convert(dataLine)));
        }

        public bool IsMatch(double[] rule, double[] dataValues)
        {
            int j = 0;
            for (int i = 0; i < Config.RuleLength - 1; i += 2)
            {
                double[] genePair = new double[] { rule[i], rule[i + 1] };
                double dataValue = dataValues[j];

                // Return false if a value is outside of the rule pair.
                if (genePair[0] < genePair[1])
                {
                    if (dataValue < genePair[0] || dataValue > genePair[1])
                    {
                        return false;
                    }
                }
                else
                {
                    if (dataValue < genePair[1] || dataValue > genePair[0])
                    {
                        return false;
                    }
                }

                // Increment to iterate through the data values.
                j++;
            }

            return true;
        }

        private void TestEachRule(double[] dataValues)
        {
            for (int i = 0; i < Config.RulesPerIndividual; i++)
            {
                double[] rule = this.GetRule(i);

                if (this.IsMatch(rule, dataValues))
                {
                    // If the predicted output matches the actual output then increment fitness.
                    if (rule[Config.RuleLength - 1] == dataValues[(Config.RuleLength - 1) / 2])
                    {
                        this.Fitness++;
                    }

                    // If there is a match, stop checking the rules.
                    break;
                }
            }
        }
    }
}
