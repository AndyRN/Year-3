using DataMiner.Classes;
using DataMiner.Helpers;
using System.Collections.Generic;

namespace DataMiner.Controllers
{
    public class EvolutionController
    {
        public List<Individual> SinglePointCrossover(List<Individual> parents)
        {
            if (RandomNumber.GenerateProbability() < Config.CrossoverProbability)
            {
                List<Individual> children = new List<Individual>();
                Individual parent1 = parents[0];
                Individual parent2 = parents[1];

                int crossoverPoint = RandomNumber.Generate(Config.ChromosomeLength);

                // Create a new chromosome with the head of the first chromosome, then append the tail of the second chromosome to it.
                int[] newChromosome1 = parent1.Chromosome.GetRange(0, crossoverPoint);
                newChromosome1 = newChromosome1.Append(parent2.Chromosome.GetRange(crossoverPoint, Config.ChromosomeLength - crossoverPoint));

                // Create another new chromosome with the head of the second chromosome, then append the tail of the first chromosome to it.
                int[] newChromosome2 = parent2.Chromosome.GetRange(0, crossoverPoint);
                newChromosome2 = newChromosome2.Append(parent1.Chromosome.GetRange(crossoverPoint, Config.ChromosomeLength - crossoverPoint));

                // Add the newly created chromosomes to the children.
                children.Add(new Individual(newChromosome1));
                children.Add(new Individual(newChromosome2));

                return children;
            }

            // Crossover did not occur, just return the parents.
            return parents;
        }

        public int[] BitwiseMutation(Individual individual)
        {
            int[] newChromosome = new int[Config.ChromosomeLength];

            for (int i = 0; i < individual.Chromosome.Length; i++)
            {
                int gene = individual.Chromosome[i];

                if (RandomNumber.GenerateProbability() < Config.MutationProbability)
                {
                    // If the current bit is the last bit of a rule.
                    if ((i + 1) % Config.RuleLength == 0)
                    {
                        // Add the inverted state of the gene.
                        gene = (gene ^= 1);
                    }
                    else
                    {
                        // Otherwise can be 0, 1 and 2 (Wildcard "#").
                        switch (gene)
                        {
                            case 0:
                            case 1:
                                if (RandomNumber.GenerateProbability() < Config.WildcardProbability)
                                {
                                    gene = 2;
                                }
                                else
                                {
                                    gene = (gene ^= 1);
                                }
                                break;
                            case 2:
                                gene = RandomNumber.Generate(2);
                                break;
                        }
                    }
                }

                newChromosome[i] = gene;
            }

            return newChromosome;
        }
    }
}
