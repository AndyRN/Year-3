using System;

namespace DataMiner.Helpers
{
    public static class RandomNumber
    {
        private static Random r = new Random();

        public static int Generate(int max)
        {
            return r.Next(max);
        }

        public static double GenerateProbability()
        {
            return r.NextDouble() * 100;
        }

        public static double GenerateCreep()
        {
            return (r.NextDouble() * 2) - 1;
        }

        public static double GenerateDouble()
        {
            return r.NextDouble();
        }

        public static double GenerateNormal()
        {
            double u1 = r.NextDouble();
            double u2 = r.NextDouble();

            double normal = Math.Sqrt(-2 * Math.Log(u1)) *
                            Math.Cos(2 * Math.PI * u2);

            return normal * (1 / 3);
        }
    }
}
