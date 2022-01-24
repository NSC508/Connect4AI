//TODO: FOR ALL GO CLASSES, MAKE SURE TO HAVE THE CHECK THAT ALL GO FUNCTIONS ARE DONE BEFORE PROCEEDING
package neat

import (
	"math/rand"
	"math"
)
type Crossover struct {
	CROSSOVER_CHANCE float64
	C1 float64
	C2 float64
	C3 float64
	DISTANCE float64
}

func newCrossover() *Crossover{
	c := new(Crossover)
	c.CROSSOVER_CHANCE = 0.75
	c.C1 = 1.0
	c.C2 = 1.0
	c.C3 = 0.4
	c.DISTANCE = 1.0
	return c
}

func (c *Crossover) ProduceOffspring(first Genotype, second Genotype) *Genotype {
	copy_first := make([]*EdgeInfo, 0)
	copy_second := make([]*EdgeInfo, 0)

	//adds all elements of first.edges to copy_first
	go func() {
		copy_first = append(copy_first, first.edges...)	
	}()

	//adds all elements of second.edges to copy_second
	copy_second = append(copy_second, second.edges...)

	match_first := make([]*EdgeInfo, 0)
	match_second := make([]*EdgeInfo, 0)

	disjoin_first := make([]*EdgeInfo, 0)
	disjoin_second := make([]*EdgeInfo, 0)

	excess_first := make([]*EdgeInfo, 0)
	excess_second := make([]*EdgeInfo, 0)

	genes_first := len(first.edges)
	genes_second := len(second.edges)

	invmax_first := first.edges[len(first.edges)-1].innovation
	invmax_second := second.edges[len(second.edges)-1].innovation

	invmin := invmax_second
	if (invmax_first < invmax_second) {
		invmin = invmax_first
	}

	for i, e1 := range copy_first {
		for _, e2 := range copy_second {
			info_first := e1
			info_second := e2

			if (info_first.innovation == info_second.innovation) {
				match_first = append(match_first, info_first)
				match_second = append(match_second, info_second)

				remove(copy_first, info_first)
				remove(copy_second, info_second)

				i--
				genes_first--
				genes_second--
				break
			}
		}
	}

	go func() {
		for _, e := range copy_first {
			if (e.innovation > invmin) {
				excess_first = append(excess_first, e)
			} else {
				disjoin_first = append(disjoin_first, e)
			}
		}
	}()
	
	go func() {
		for _, e := range copy_second {
			if (e.innovation > invmin) {
				excess_second = append(excess_second, e)
			} else {
				disjoin_second = append(disjoin_second, e)
			}
		}
	}()
	

	child := newGenotype()

	matching := len(match_first)

	for i := 0;  i < matching; i++ {
		roll := rand.Intn(2)

		if (roll == 0 || !match_second[i].enabled) {
			child.AddEdge(match_first[i].source, match_first[i].destination, match_first[i].weight, match_first[i].enabled, match_first[i].innovation)
		} else {
			child.AddEdge(match_second[i].source, match_second[i].destination, match_second[i].weight, match_second[i].enabled, match_second[i].innovation)
		}
	}

	go func() {
		for _, e := range disjoin_first {
			child.AddEdge(e.source, e.destination, e.weight, e.enabled, e.innovation)
		}
	}()
	
	go func() {
		for _, e := range disjoin_second {
			child.AddEdge(e.source, e.destination, e.weight, e.enabled, e.innovation)
		}	
	}()
	
	child.SortEdges()

	ends := make([]*int, 0)

	for _, e := range first.vertices {
		if (e.etype == ETypeHidden) {
			break
		}
		ends = append(ends, &e.index)
		child.AddVertex(e.etype, e.index)
	}

	c.AddUniqueVertices(child, ends)

	child.SortVertices()

	return child
}

func (c *Crossover) AddUniqueVertices(genotype *Genotype, ends []*int) {
	unique := make([]*int, 0)

	for _, info := range genotype.edges {
		if (!Contains(ends, info.source) && !Contains(unique, info.source)) {
			unique = append(unique, &info.source)
		}

		if (!Contains(ends, info.destination) && !Contains(unique, info.destination)) {
			unique = append(unique, &info.destination)
		}
	}

	uniques := len(unique)

	for i := 0; i < uniques; i++ {
		genotype.AddVertex(ETypeHidden, *unique[i])
	}
}

func (c *Crossover) SpeciationDistance(first Genotype, second Genotype) float64{
	copyFirst := make([]*EdgeInfo, 0)
	copySecond := make([]*EdgeInfo, 0)

	copyFirst = append(copyFirst, first.edges...)
	copySecond = append(copySecond, second.edges...)

	matchFirst := make([]*EdgeInfo, 0)
	matchSecond := make([]*EdgeInfo, 0)

	disjoinFirst := make([]*EdgeInfo, 0)
	disjoinSecond := make([]*EdgeInfo, 0)

	excessFirst := make([]*EdgeInfo, 0)
	excessSecond := make([]*EdgeInfo, 0)

	genesFirst := len(first.edges)
	genesSecond := len(second.edges)

	invmaxFirst := first.edges[len(first.edges)-1].innovation
	invmaxSecond := second.edges[len(second.edges)-1].innovation

	invmin := invmaxSecond
	if (invmaxFirst < invmaxSecond) {
		invmin = invmaxFirst
	}

	diff := 0.0

	for i, e1 := range copyFirst {
		for _, e2 := range copySecond {
			infoFirst := e1
			infoSecond := e2

			if (infoFirst.innovation == infoSecond.innovation) {
				weightDiff := math.Abs(infoFirst.weight - infoSecond.weight)
				diff += weightDiff

				matchFirst = append(matchFirst, infoFirst)
				matchSecond = append(matchSecond, infoSecond)

				remove(copyFirst, infoFirst)
				remove(copySecond, infoSecond)

				i--
				genesFirst--
				genesSecond--
				break
			}
		}
	}

	go func() {
		for _, e1 := range copyFirst {
			if (e1.innovation > invmin) {
				excessFirst = append(excessFirst, e1)
			} else {
				disjoinFirst = append(disjoinFirst, e1)
			}
		}
	}()
	
	go func(){
		for _, e2 := range copySecond {
			if (e2.innovation > invmin) {
				excessSecond = append(excessSecond, e2)
			} else {
				disjoinSecond = append(disjoinSecond, e2)
			}
		}
	}()

	match := len(matchFirst)
	disjoint := len(disjoinFirst) + len(disjoinSecond)
	excess := len(excessFirst) + len(excessSecond)

	n := Max(len(first.edges), len(second.edges))
	E := float64(excess) / float64(n)
	D := float64(disjoint) / float64(n)
	W := diff / float64(match)

	return E * c.C1 + D * c.C2 + W * c.C3
}

func Max(x, y int) int {
	if (x > y) {
		return x
	} else {
		return y
	}
}

func remove(arr []*EdgeInfo, e *EdgeInfo) []*EdgeInfo {
	for i, a := range arr {
		if (a == e) {
			return append(arr[:i], arr[i+1:]...)
		}
	}
	return arr
}

func Contains(arr []*int, e int) bool {
	for _, a := range arr {
		if (*a == e) {
			return true
		}
	}
	return false
}
