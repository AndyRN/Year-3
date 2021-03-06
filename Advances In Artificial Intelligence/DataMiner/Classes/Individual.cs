﻿using DataMiner.Helpers;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace DataMiner.Classes
{
    public class Individual
    {
        public int[] Chromosome { get; set; }
        public int Fitness { get; set; }
        public double SuccessRate { get; set; }

        public Individual()
        {
            this.SuccessRate = 0;

            this.Chromosome = new int[Config.ChromosomeLength];
            for (int i = 0; i < Config.ChromosomeLength; i++)
            {
                // If the current bit is the last bit of a rule.
                if ((i + 1) % Config.RuleLength == 0)
                {
                    // Can only be 0 or 1.
                    this.Chromosome[i] = RandomNumber.Generate(2);
                }
                else
                {
                    // Otherwise can be 0, 1 and 2 (Wildcard "#").
                    this.Chromosome[i] = RandomNumber.Generate(3);
                }
            }
        }

        public Individual(int[] chromosome)
        {
            this.UpdateChromosome(chromosome);
        }

        public Individual Clone()
        {
            return new Individual
            {
                Fitness = this.Fitness,
                Chromosome = this.Chromosome
            };
        }

        public void UpdateChromosome(int[] chromosome)
        {
            this.Chromosome = chromosome;
        }

        public int[] GetRule(int ruleNumber)
        {
            return this.Chromosome.GetRange(ruleNumber * Config.RuleLength, Config.RuleLength);
        }

        public void EvaluateFitness()
        {
            this.Fitness = 0;

            // Threading to improve performance.
            Parallel.ForEach(Config.LearningData, dataLine => this.TestEachRule(DataConverter.Convert(dataLine)));
        }

        public bool IsMatch(int[] rule, int[] dataBits)
        {
            for (int i = 0; i < Config.RuleLength - 1; i++)
            {
                int ruleBit = rule[i];

                // Compare each bit until one difference is found, otherwise the rule is a match.
                if (ruleBit != dataBits[i] && ruleBit != 2)
                {
                    return false;
                }
            }

            return true;
        }

        private void TestEachRule(int[] dataBits)
        {
            for (int i = 0; i < Config.RulesPerIndividual; i++)
            {
                int[] rule = this.GetRule(i);

                if (this.IsMatch(rule, dataBits))
                {
                    // If the outputs are the same then increment the fitness.
                    if (rule[Config.RuleLength - 1] == dataBits[Config.RuleLength - 1])
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
